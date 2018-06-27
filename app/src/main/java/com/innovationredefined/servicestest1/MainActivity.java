package com.innovationredefined.servicestest1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Arpan Bag on 26 June, 2018
 */

public class MainActivity extends AppCompatActivity {
    Intent serviceIntent;
    ServiceConnection serviceConnection;
    WatcherService watcherService;
    boolean isServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_Start = findViewById(R.id.button);
        Button button_Stop = findViewById(R.id.button2);

        serviceIntent = new Intent(getApplicationContext(), WatcherService.class);

        button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start service
                startService(serviceIntent);

                //Bind service
                if (serviceConnection == null) {
                    serviceConnection = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            WatcherService.WatcherServiceBinder watcherServiceBinder = (WatcherService.WatcherServiceBinder) service;
                            watcherService = watcherServiceBinder.getService();
                            isServiceBound = true;
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            isServiceBound = false;
                        }
                    };
                }
                bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        button_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceBound) {
                    unbindService(serviceConnection);
                    isServiceBound = false;
                }
                stopService(serviceIntent);
            }
        });
    }
}

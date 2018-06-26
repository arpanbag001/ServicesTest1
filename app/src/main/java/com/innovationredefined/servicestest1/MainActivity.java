package com.innovationredefined.servicestest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Arpan Bag on 26 June, 2018
 */

public class MainActivity extends AppCompatActivity {
    Intent serviceIntent;

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
                startService(serviceIntent);
            }
        });

        button_Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });
    }
}

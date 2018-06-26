package com.innovationredefined.servicestest1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Arpan Bag on 26 June, 2018
 */

public class WatcherService extends Service {

    Context context = this;
    Handler handler = new Handler();

    class WatcherServiceBinder extends Binder{
        public WatcherService getService(){
            return WatcherService.this;
        }
    }

    private WatcherServiceBinder watcherServiceBinder = new WatcherServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onBind() called", Toast.LENGTH_SHORT).show();
        return watcherServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand() called", Toast.LENGTH_SHORT).show();
        executeTask();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy() called", Toast.LENGTH_SHORT).show();
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    void executeTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, SecondActivity.class));
            }
        }, 5000);
    }
}

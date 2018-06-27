package com.innovationredefined.servicestest1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Arpan Bag on 26 June, 2018
 */

public class WatcherService extends Service {

    Context context = this;
    Handler handler = new Handler();
    Notification notification;

    class WatcherServiceBinder extends Binder {
        public WatcherService getService() {
            return WatcherService.this;
        }
    }

    private WatcherServiceBinder watcherServiceBinder = new WatcherServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate() called", Toast.LENGTH_SHORT).show();
        handleNotification();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onBind() called", Toast.LENGTH_SHORT).show();
        return watcherServiceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand() called", Toast.LENGTH_SHORT).show();
        startForeground(1, notification);
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
                startActivity(new Intent(context, SecondActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }, 5000);
    }

    void handleNotification() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


        Notification.Builder builder;
        if (Build.VERSION.SDK_INT < 26) {
            builder = new Notification.Builder(this);
        } else {
            String NOTIFICATION_CHANNEL_ID = "Watcher Service Notification Channel ID";
            final CharSequence ChannelName = "Watcher Service Notification Channel";
            ((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, ChannelName, NotificationManager.IMPORTANCE_LOW));
            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
        }

        notification = builder
                .setContentTitle("Watcher Service")
                .setContentText("Protecting your device")
                .setSmallIcon(R.drawable.ic_security_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
    }
}

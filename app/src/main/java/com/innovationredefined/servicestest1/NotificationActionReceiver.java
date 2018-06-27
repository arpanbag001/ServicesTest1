package com.innovationredefined.servicestest1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Arpan Bag on 27 June, 2018
 */

public class NotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(AppConstants.ACTION_STOP_SERVICE)) {
            Intent service = new Intent(context, WatcherService.class);
            context.getApplicationContext().stopService(service);
        }
    }
}

package com.example.excelme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder builder =  helper.getAlarmNotification("Wake up!",
                "It is time to wake up and start a wonderful day");
        helper.getManager().notify(1, builder.build());
    }
}

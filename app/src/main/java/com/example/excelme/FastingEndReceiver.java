package com.example.excelme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class FastingEndReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder builder = helper.getAlarmNotification("Time to eat!",
                "Time to eat something healthy and tasty", NotificationHelper.FASTING_ALARM_END_ID);
        helper.getManager().notify(1, builder.build());
    }
}

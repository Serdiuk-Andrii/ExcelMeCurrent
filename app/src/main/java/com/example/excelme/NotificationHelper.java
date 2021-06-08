package com.example.excelme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String ALARM_CLOCK_ID = "alarmClock";
    public static final String ALARM_CLOCK_NAME = "Alarm";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            createChannels();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel alarmNotificationChannel = new NotificationChannel(ALARM_CLOCK_ID,
                ALARM_CLOCK_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        alarmNotificationChannel.enableLights(true);
        alarmNotificationChannel.enableVibration(true);
        alarmNotificationChannel.setLightColor(R.color.design_default_color_primary);
        alarmNotificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(alarmNotificationChannel);
    }

    NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public NotificationCompat.Builder getAlarmNotification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), ALARM_CLOCK_ID)
                .setContentTitle(title).setContentText(message)
                .setSmallIcon(R.drawable.ic_alarm);
    }

}

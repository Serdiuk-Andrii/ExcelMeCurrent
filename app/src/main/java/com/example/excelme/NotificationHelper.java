/*This class is a helper class for notifications such as alarm clock
 * File: NotificationHelper.java
 * Author: Serdiuk Andrii
 * */



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
    public static final String FASTING_ALARM_END_ID = "fasting_alarm_stop";
    public static final String FASTING_CLOCK_NAME = "Intermittent fasting";
    public static final String HABITS_ALARM_NAME = "habits";
    public static final String HABITS_ID = "habits";

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

        NotificationChannel fastingChannel = new NotificationChannel(FASTING_ALARM_END_ID, FASTING_CLOCK_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        alarmNotificationChannel.enableLights(true);
        alarmNotificationChannel.enableVibration(true);
        alarmNotificationChannel.setLightColor(R.color.design_default_color_primary);
        alarmNotificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationChannel habitsChannel = new NotificationChannel(HABITS_ID, HABITS_ALARM_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        habitsChannel.enableLights(true);
        habitsChannel.enableVibration(true);
        habitsChannel.setLightColor(R.color.design_default_color_primary);
        habitsChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager manager = getManager();
        manager.createNotificationChannel(alarmNotificationChannel);
        manager.createNotificationChannel(fastingChannel);
        manager.createNotificationChannel(habitsChannel);
    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public NotificationCompat.Builder getAlarmNotification(String title, String message, String notificationId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), notificationId)
                .setContentTitle(title).setContentText(message);
        switch (notificationId) {
            case ALARM_CLOCK_ID:
                builder.setSmallIcon(R.drawable.ic_alarm);
                break;
            case FASTING_ALARM_END_ID:
                builder.setSmallIcon(R.drawable.ic_notification_fasting);
                break;
            case HABITS_ID:
                builder.setSmallIcon(R.drawable.ic_habits);
                break;
        }
        return builder;
    }

}

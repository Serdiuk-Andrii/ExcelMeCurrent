/*This class is responsible for creating notifications about habits
* File: HabitsReceiver.java
* Author: Serdiuk Andrii
**/

package com.example.excelme.habits;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

import com.example.excelme.AlertReceiver;
import com.example.excelme.NotificationHelper;

public class HabitsReceiver extends BroadcastReceiver {

    private String habitName;
    private SharedPreferences sharedPref;

    public HabitsReceiver(String habitName) {
        this.habitName = habitName;
    }

    public HabitsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
            startRescheduleAlarmsService(context);
        else {
            NotificationHelper helper = new NotificationHelper(context);
            NotificationCompat.Builder builder = helper.getAlarmNotification("Habits time!",
                    habitName, NotificationHelper.ALARM_CLOCK_ID);
            helper.getManager().notify(1, builder.build());
        }
    }

    private void startRescheduleAlarmsService(Context context) {
        sharedPref = context.getSharedPreferences("habit", Context.MODE_PRIVATE);
        long time = sharedPref.getLong("habits_time", 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);

    }




}

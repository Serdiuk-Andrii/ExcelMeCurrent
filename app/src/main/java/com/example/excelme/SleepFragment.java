package com.example.excelme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;


public class SleepFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

    private Activity activity;
    private TextView alarmTextView;
    private static final MaterialTimePicker.Builder builder =
            new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setTitleText("Alarm clock");


    public SleepFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alarmTextView = view.findViewById(R.id.alarmText);
        alarmTextView.setText("No alarm set yet");

        Button buttonSetTime = view.findViewById(R.id.setAlarm);
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker picker = builder.build();
                picker.show(getChildFragmentManager(), "tag");

            }
        });

        Button buttonCancelAlarm = view.findViewById(R.id.cancelAlarm);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
        activity = getActivity();
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        alarmTextView.setText("No alarm set yet");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        startAlarm(calendar);


    }

    private void updateTimeText(Calendar calendar) {
        alarmTextView.setText("Alarm set at: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


}
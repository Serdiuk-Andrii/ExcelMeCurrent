/*This class defined the sleep menu
 * File: SleepFragment.java
 * Author: Serdiuk Andrii
 * */


package com.example.excelme.menuFragmetns;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.excelme.AlertReceiver;
import com.example.excelme.MainFragment;
import com.example.excelme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;


public class SleepFragment extends Fragment{

    private TextView alarmTextView;
    private Activity activity;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private NavController controller;
    private BottomNavigationView menu;


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
        controller = Navigation.findNavController(view);
        Button buttonSetTime = view.findViewById(R.id.setAlarm);
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new com.example.excelme.TimePicker(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.SECOND, 0);

                                updateTimeText(calendar);
                                startAlarm(calendar);
                            }
                        }

                );
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
        sharedPref = activity.getSharedPreferences("sleep", Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        menu = view.findViewById(R.id.bottom_navigation);
        if(MainFragment.animationOn)
            menu.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));

        menu.setSelectedItemId(R.id.page_4);


        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.page_1:
                        controller.navigate(R.id.action_global_sportFragment);
                        break;
                    case R.id.page_2:
                        controller.navigate(R.id.action_global_chooseFoodSection);
                        break;
                    case R.id.page_3:
                        controller.navigate(R.id.action_global_habitsFragment);
                        break;
                    case R.id.page_4:
                        controller.navigate(R.id.action_global_sleepFragment);
                    default:
                        return false;
                }


                return true;
            }
        });
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        alarmTextView.setText("No alarm set yet");
    }

    private void updateTimeText(Calendar calendar) {
        alarmTextView.setText("Alarm set at: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
    }

    private void startAlarm(Calendar calendar) {
        long time = calendar.getTimeInMillis();
        editor.putLong("alarm_time", time);
        editor.apply();
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }





}
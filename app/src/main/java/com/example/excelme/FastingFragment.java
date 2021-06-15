/*This class is responsible for the intermittent fasting menu
* File: FastingFragment.java
* Author: Serdiuk Andrii
**/


package com.example.excelme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class FastingFragment extends Fragment {

    private double timeToFast;
    private boolean isRunning;
    private Intervals interval = Intervals.VARIANT14AND10;
    private long millisecondsLeft;
    private long endTime;
    public static final long MILLISECONDS_IN_HOUR = 3600000;
    public static final long MILLISECONDS_IN_MINUTE = 60000;
    public static final long MILLISECONDS_IN_SECOND = 1000;
    private Activity context;
    private ProgressBar progressBar;
    private Button startFasting, stopFasting;
    private CountDownTimer timer;
    private TextView progressText;
    private RadioGroup intervals;
    private RadioButton firstInterval, secondInterval, thirdInterval, fourthInterval;
    private Animation blink;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Dialog dialog;


    private enum Intervals {
        VARIANT16AND8, VARIANT20AND4, VARIANT14AND10, VARIANT18AND6;

        private static int getFastingTime(Intervals interval) {
            switch (interval) {
                case VARIANT14AND10:
                    return 14;
                case VARIANT16AND8:
                    return 16;
                case VARIANT18AND6:
                    return 18;
                case VARIANT20AND4:
                    return 20;
                default:
                    return 0;
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        blink = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        dialog = new Dialog(context);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fasting, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar);
        startFasting = view.findViewById(R.id.start_fasting);
        stopFasting = view.findViewById(R.id.stop_fasting);
        stopFasting.setVisibility(View.INVISIBLE);
        startFasting.setOnClickListener(v -> {
            startFasting.setVisibility(View.INVISIBLE);
            //stopFasting.startAnimation(blink);
            millisecondsLeft = Intervals.getFastingTime(interval) * MILLISECONDS_IN_HOUR;
            if (interval == Intervals.VARIANT20AND4)
                millisecondsLeft = 120000;
            timeToFast = millisecondsLeft;
            intervals.setVisibility(View.INVISIBLE);
            startFastingProcess();
        });
        stopFasting.setOnClickListener(v ->
        {
            startFasting.setVisibility(View.VISIBLE);
            //startFasting.startAnimation(blink);
            millisecondsLeft = 0;
            isRunning = false;
            timer.cancel();
            progressBar.setProgress(0, true);
            intervals.setVisibility(View.VISIBLE);
            progressText.setText("");
            stopFasting.setVisibility(View.INVISIBLE);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, FastingEndReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

            alarmManager.cancel(pendingIntent);
        });
        progressText = view.findViewById(R.id.text_view_progress);
        (firstInterval = view.findViewById(R.id.firstInterval)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interval = Intervals.VARIANT14AND10;
            }
        });
        (secondInterval = view.findViewById(R.id.secondInterval)).setOnClickListener(v -> interval = Intervals.VARIANT16AND8);
        (thirdInterval = view.findViewById(R.id.thirdInterval)).setOnClickListener(v -> interval = Intervals.VARIANT18AND6);
        (fourthInterval = view.findViewById(R.id.fourthInterval)).setOnClickListener(v -> interval = Intervals.VARIANT20AND4);
        intervals = view.findViewById(R.id.intervals);
        sharedPref = context.getSharedPreferences("sleep", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        NavController controller = Navigation.findNavController(view);
        BottomNavigationView menu = view.findViewById(R.id.bottom_navigation);
        menu.setSelectedItemId(R.id.page_2);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
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

    private void setupNotification() {
        editor.putLong("fasting_time", endTime);
        editor.apply();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, FastingEndReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, pendingIntent);
    }

    private void startFastingProcess() {
        endTime = System.currentTimeMillis() + millisecondsLeft;
        stopFasting.setVisibility(View.VISIBLE);
        setupNotification();
        timer = new CountDownTimer(millisecondsLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisecondsLeft = millisUntilFinished;
                updateCounter();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                stopFastingProcess();
                stopFasting.setVisibility(View.INVISIBLE);
                intervals.setVisibility(View.VISIBLE);

            }
        }.start();
        isRunning = true;
        updateCounter();

    }


    private void updateCounter() {
        int hours = (int) (millisecondsLeft / MILLISECONDS_IN_HOUR);
        int minutes = (int) ((millisecondsLeft / MILLISECONDS_IN_MINUTE) % 60);
        int seconds = (int) ((millisecondsLeft / MILLISECONDS_IN_SECOND) % 60);
        progressText.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(100 - ((int) (100 * millisecondsLeft / timeToFast)), true);
        }

    }

    private void stopFastingProcess() {
        stopFasting.setVisibility(View.INVISIBLE);
        startFasting.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        //startFasting.startAnimation(blink);
        millisecondsLeft = 0;
        timer.cancel();
        showDialog();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = context.getSharedPreferences("timer", MODE_PRIVATE);
        isRunning = prefs.getBoolean("timerRunning", false);
        if (isRunning) {
            endTime = prefs.getLong("endTime", 0);
            timeToFast = prefs.getLong("timeToFast", 0);
            millisecondsLeft = endTime - System.currentTimeMillis();
            if (millisecondsLeft < 0) {
                intervals.setVisibility(View.VISIBLE);
                millisecondsLeft = 0;
                isRunning = false;
                showDialog();
                editor.putBoolean("timerRunning", false);
            } else {
                startFastingProcess();
                intervals.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = context.getSharedPreferences("timer", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("timerRunning", isRunning);
        editor.putLong("endTime", endTime);
        editor.putLong("timeToFast", (long) timeToFast);
        editor.apply();
        if (timer != null)
            timer.cancel();
    }

    private void showDialog() {
        dialog.setContentView(R.layout.congratulations);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialButton button = dialog.findViewById(R.id.stop_congratulations);
        button.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}
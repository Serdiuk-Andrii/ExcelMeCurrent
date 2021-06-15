/*This class is responsible for loading and showing habits that the user has created
* File: HabitsFragment.java
* Author: Serdiuk Andrii
**/


package com.example.excelme.menuFragmetns;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.excelme.MainFragment;
import com.example.excelme.R;
import com.example.excelme.habits.Habit;
import com.example.excelme.habits.HabitsReceiver;
import com.example.excelme.habits.HabitsRecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public class HabitsFragment extends Fragment {

    private NavController controller;
    private BottomNavigationView menu;
    private RecyclerView habitsRecyclerView;
    private Activity activity;
    private FloatingActionButton add;
    private HabitsRecyclerViewAdapter adapter;
    private Dialog dialog;
    private Set<String> habitsSet = new HashSet<>();
    private static long currentFireTime;


    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habits, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        dialog = new Dialog(activity);
        setupMenu(view);
        habitsRecyclerView = view.findViewById(R.id.habits_recycler_view);
        adapter = new HabitsRecyclerViewAdapter(activity);
        fillInDataAboutHabits(adapter);
        habitsRecyclerView.setAdapter(adapter);
        habitsRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        add = view.findViewById(R.id.add_habit);
        setupAddButton(view);
    }

    private void setupAddButton(View view) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.add_habit_dialog);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                MaterialButton button = dialog.findViewById(R.id.stop_congratulations);
                TextInputEditText name, description, firetime;
                name = dialog.findViewById(R.id.habit_name);
                description = dialog.findViewById(R.id.habit_description);

                firetime = dialog.findViewById(R.id.habit_firetime);
                firetime.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                TextInputLayout firetimeLayout = dialog.findViewById(R.id.habit_firetime_layout);
                firetimeLayout.setEndIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new com.example.excelme.TimePicker(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                if (c.before(Calendar.getInstance()))
                                    c.add(Calendar.DATE, 1);
                                //Setting up the alarm clock
                                currentFireTime = c.getTimeInMillis();
                                AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                                Intent intent = new Intent(activity, HabitsReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 1, intent, 0);
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentFireTime, pendingIntent);

                                editor.putLong("habits_time", currentFireTime);
                                editor.apply();
                                //Updating the text and the button
                                firetime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
                                if (!name.getText().toString().isEmpty() && !firetime.getText().toString().isEmpty()) {

                                    button.setEnabled(true);
                                }

                            }

                        }

                        );
                        picker.show(getChildFragmentManager(), "tag");
                    }
                });

                button.setOnClickListener(a ->
                {
                    String habitName = name.getText().toString();
                    String habitDescription = description.getText().toString();
                    adapter.addHabit(new Habit(habitName, habitDescription,
                            firetime.getText().toString()));
                    habitsSet = new HashSet<>(sharedPref.getStringSet("habits_set", new HashSet<>()));
                    habitsSet.add(habitName + '<' + habitDescription + '<' + firetime.getText().toString());
                    editor.putStringSet("habits_set", habitsSet);
                    editor.apply();
                    dialog.dismiss();
                }
                );

                dialog.show();
            }
        });
    }

    private void fillInDataAboutHabits(HabitsRecyclerViewAdapter adapter) {
        for (String s: sharedPref.getStringSet("habits_set", new HashSet<>())) {
            String[] values = s.split("<");
            adapter.addHabit(new Habit(values[0], values[1], values[2]));
       }
    }

    private void setupMenu(View view) {
        controller = Navigation.findNavController(view);
        menu = view.findViewById(R.id.bottom_navigation);
        if(MainFragment.animationOn)
            menu.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
        menu.setSelectedItemId(R.id.page_3);
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







}
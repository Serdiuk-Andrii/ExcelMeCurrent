/*This class is a realization of the recycler view for all the habits that the user can add
* File: HabitsRecyclerViewAdapter.java
* Author: Serdiuk Andrii
**/


package com.example.excelme.habits;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.excelme.FastingEndReceiver;
import com.example.excelme.FastingFragment;
import com.example.excelme.R;
import com.example.excelme.RecipeListAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HabitsRecyclerViewAdapter extends RecyclerView.Adapter<HabitsRecyclerViewAdapter.ViewHolder>{

    private List<Habit> habits = new ArrayList<>();
    private Set<String> habitsSet = new HashSet<>();
    private Activity context;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public HabitsRecyclerViewAdapter(Activity context) {
        this.context = context;
        sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setHabits(List<Habit> habits) {
        this.habits.addAll(habits);
        notifyDataSetChanged();
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
        notifyItemInserted(habits.size() - 1);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_list_item, parent,
                false);
        return new HabitsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HabitsRecyclerViewAdapter.ViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.habitName.setText(habit.name);
        holder.dueTo.setText(habit.fireTime);
        String[] time = holder.dueTo.getText().toString().split(":");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        c.set(Calendar.SECOND, 0);
        if (c.before(Calendar.getInstance()))
            c.add(Calendar.DATE, 1);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, FastingEndReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        holder.parent.setOnClickListener(v ->
        {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.habit_expanded);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView habitName = dialog.findViewById(R.id.habit_expanded_name);
            TextView habitDescription = dialog.findViewById(R.id.habit_expanded_description);
            TextView habitTime = dialog.findViewById(R.id.habit_expanded_time);
            habitName.setText(habit.name);
            habitDescription.setText(habit.description);
            habitTime.setText(habit.fireTime);
            MaterialButton button = dialog.findViewById(R.id.habit_expanded_accept);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            MaterialButton delete = dialog.findViewById(R.id.habit_expanded_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    habits.remove(habit);
                    notifyItemRemoved(position);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.fadeOutAnimation;
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.dismiss();
                    HashSet<String> habitsSet = new HashSet<>(sharedPref.getStringSet("habits_set", new HashSet<>()));
                    habitsSet.remove(habit.name + '<' + habit.description + '<' + habit.fireTime);
                    editor.putStringSet("habits_set", habitsSet);
                    editor.apply();
                }
            });
            dialog.show();
        });
        holder.parent.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_down));
    }

    private void setupNotification(long fireTime) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, FastingEndReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, fireTime, pendingIntent);
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView habitName;
        private TextView dueTo;
        private MaterialCardView parent;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitsName);
            dueTo = itemView.findViewById(R.id.dueTo);
            parent = itemView.findViewById(R.id.habit_parent);
        }
    }


}

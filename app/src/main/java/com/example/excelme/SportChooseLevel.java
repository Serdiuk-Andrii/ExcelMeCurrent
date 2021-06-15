/*This class is responsible for correct navigating from SportChooseLevel fragment
* to exercise preparation
* File: SportChooseLevel.java
* Author: Serdiuk Andrii
 */

package com.example.excelme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

public class SportChooseLevel extends Fragment {

    private NavController controller;

    public enum CurrentTraining {
        ARMS, LEGS, ABS, FULLBODY;
    };

    public static CurrentTraining currentTraining;

    public static final String COMPLEXITY_EASY = "easy";
    public static final String COMPLEXITY_MEDIUM = "medium";
    public static final String COMPLEXITY_HARD = "hard";
    public static final int COMPLEXITY_EASY_AMOUNT = 4;
    public static final int COMPLEXITY_MEDIUM_AMOUNT = 5;
    public static final int COMPLEXITY_HARD_AMOUNT = 5;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport_choose_level, container, false);
    }

    private String getCurrentTraining() {
        switch (currentTraining) {
            case ABS:
                return "ABS";
            case LEGS:
                return "LEGS";
            case ARMS:
                return "ARMS";
            case FULLBODY:
                return "FULL BODY";
            default:
                return "";
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        controller = Navigation.findNavController(view);

                ImageView easyIcon = view.findViewById(R.id.easy_icon);
                ImageView mediumIcon = view.findViewById(R.id.medium_icon);
                ImageView hardIcon = view.findViewById(R.id.hard_icon);
                MaterialCardView easyCard = view.findViewById(R.id.easyTrainingCard);
                MaterialCardView mediumCard = view.findViewById(R.id.mediumTrainingCard);
                MaterialCardView hardCard = view.findViewById(R.id.hardTrainingCard);
                easyCard.setOnClickListener(new WorkoutClickListener(COMPLEXITY_EASY));
                mediumCard.setOnClickListener(new WorkoutClickListener(COMPLEXITY_MEDIUM));
                hardCard.setOnClickListener(new WorkoutClickListener(COMPLEXITY_HARD));
                TextView workoutType = view.findViewById(R.id.workoutType);
                workoutType.setText(getCurrentTraining() + " WORKOUTS");
                switch (currentTraining) {
                    case ABS:
                        easyIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_abs_beginner));
                        mediumIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_abs_medium));
                        hardIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_abs_advanced));
                        break;
                    case LEGS:
                        easyIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_legs_beginner));
                        mediumIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_legs_medium));
                        hardIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_legs_advanced));
                        break;
                    case FULLBODY:
                        easyIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_full_body_beginner));
                        mediumIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_full_body_medium));
                        hardIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_full_body_advanced));
                        break;
                    case ARMS:
                        easyIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_arms_beginner));
                        mediumIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_arms_medium));
                        hardIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_arms_advanced));
                        break;
                    default:
                        Toast.makeText(activity, "An error occurred loading " + getCurrentTraining() + " messages", Toast.LENGTH_LONG).show();
                        break;
                }


    }

    private class WorkoutClickListener implements View.OnClickListener {

        private final String complexity;

        public WorkoutClickListener(String complexity) {
            this.complexity = complexity;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("section", getCurrentTraining().toLowerCase() + "/" + complexity);
            getParentFragmentManager().setFragmentResult("section", bundle);
            controller.navigate(R.id.action_sportChooseLevel_to_exerciseFragment);
        }
    }



}
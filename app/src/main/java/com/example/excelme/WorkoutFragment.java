package com.example.excelme;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;


public class WorkoutFragment extends Fragment {

    private Activity context;
    private NavController controller;
    private ImageView currentView;
    private TextView currentText;
    private ConstraintLayout workoutLayout;
    private String[] names;
    private String[] urls;
    private int id = 1;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        currentText = view.findViewById(R.id.workoutDescription);
        currentView = view.findViewById(R.id.workoutImage);
        workoutLayout = view.findViewById(R.id.workoutLayout);
        controller = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getParentFragmentManager().setFragmentResultListener("exercises", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                names = result.getStringArray("names");
                urls = result.getStringArray("urls");
                currentText.setText(names[0]);
                Glide.with(context).asGif().load(urls[0]).into(currentView);
                workoutLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id != names.length) {
                            currentText.setText(names[id]);
                            Glide.with(context).asGif().load(urls[id++]).into(currentView);
                        }
                        else
                            controller.navigate(R.id.action_workoutFragment_to_workoutCongratulationsFragment);
                    }
                });
            }
        });



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }
}
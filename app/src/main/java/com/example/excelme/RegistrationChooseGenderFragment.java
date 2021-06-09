package com.example.excelme;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import org.jetbrains.annotations.NotNull;

public class RegistrationChooseGenderFragment extends Fragment {

    private RegistrationActivity activity;
    private RadioButton maleButton;
    private RadioButton femaleButton;

    public RegistrationChooseGenderFragment(RegistrationActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_choose_gender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        maleButton = view.findViewById(R.id.male_button);
        femaleButton = view.findViewById(R.id.female_button);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setTextColor(Color.WHITE);
                femaleButton.setTextColor(Color.BLACK);
            }
        });
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setTextColor(Color.BLACK);
                femaleButton.setTextColor(Color.WHITE);
            }
        });
    }
}
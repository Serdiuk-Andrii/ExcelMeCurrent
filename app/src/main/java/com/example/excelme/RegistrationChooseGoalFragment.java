package com.example.excelme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegistrationChooseGoalFragment extends Fragment {

    private RegistrationActivity activity;

    public RegistrationChooseGoalFragment(RegistrationActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_choose_goal, container, false);
    }
}
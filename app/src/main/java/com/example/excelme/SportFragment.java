/*This class defined the sport menu
 * File: SportFragment.java
 * Author: Serdiuk Andrii
 * */



package com.example.excelme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;


public class SportFragment extends Fragment {

    NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        view.findViewById(R.id.arms).setOnClickListener(new SportFragment.SportNavigator("ARMS"));
        view.findViewById(R.id.legs).setOnClickListener(new SportFragment.SportNavigator("LEGS"));
        view.findViewById(R.id.abs).setOnClickListener(new SportFragment.SportNavigator("ABS"));
        view.findViewById(R.id.full_body).setOnClickListener(new SportFragment.SportNavigator("FULL BODY"));
    }

    private class SportNavigator implements View.OnClickListener {

        String destination;

        private SportNavigator(String s) {
            destination = s;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("destination", destination);
            getParentFragmentManager().setFragmentResult("destination", bundle);
            SportFragment.this.controller.navigate(R.id.action_sportFragment_to_sportChooseLevel);
        }
    }


}
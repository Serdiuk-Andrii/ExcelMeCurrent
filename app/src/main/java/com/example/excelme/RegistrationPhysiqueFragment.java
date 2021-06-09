package com.example.excelme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class RegistrationPhysiqueFragment extends Fragment {


    private RegistrationActivity activity;
    private TextInputEditText weight;
    private TextInputEditText height;

    public RegistrationPhysiqueFragment(RegistrationActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.weight = view.findViewById(R.id.registration_input_weight);
        this.height = view.findViewById(R.id.registration_input_height);
        weight.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "110")});
        height.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "230")});

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_physique, container, false);
    }
}
package com.example.excelme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;


public class AboutMeFragment extends Fragment {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private AppCompatRadioButton maleButton;
    private AppCompatRadioButton femaleButton;
    private FragmentActivity context;
    private final MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select your birthday").build();
    private String username;
    private boolean isMale;
    private short height;
    private short weight;
    private TextInputEditText usernameInput;
    private TextInputEditText heightInput;
    private TextInputEditText weightInput;
    private TextInputEditText birthdayInput;
    private MaterialButton saveButton;

    @Override
    public void onAttach(@NotNull Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        setupInputs(view);
        isMale = sharedPref.getBoolean(getString(R.string.genderKey), true);
        maleButton = view.findViewById(R.id.male_button);
        femaleButton = view.findViewById(R.id.female_button);
        saveButton = view.findViewById(R.id.save_settings);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(getString(R.string.genderKey), true).
                        putString(getString(R.string.usernameKey), usernameInput.getText().toString())
                        .putInt(getString(R.string.heightKey), Integer.parseInt(heightInput.getText().toString()))
                        .putInt(getString(R.string.weightKey), Integer.parseInt(weightInput.getText().toString()))
                        .putString(getString(R.string.birthdayKey), birthdayInput.getText().toString())
                        .apply();
                Toast.makeText(context, getString(R.string.settings_changed), Toast.LENGTH_SHORT).show();
            }
        });
        setupGenderButtons();
    }

    private void setupInputs(View view) {
        username = sharedPref.getString(getString(R.string.usernameKey), "");
        height = (short) sharedPref.getInt(getString(R.string.heightKey), 0);
        weight = (short) sharedPref.getInt(getString(R.string.weightKey), 0);
        //Filling in the fields
        usernameInput = (TextInputEditText) view.findViewById(R.id.input_name);
        heightInput = (TextInputEditText) view.findViewById(R.id.input_height);
        weightInput = (TextInputEditText) view.findViewById(R.id.input_weight);
        if (username.length() > 0)
            usernameInput.setText(username);
        if (height > 0)
            heightInput.setText(String.valueOf(height));
        if (weight > 0)
            weightInput.setText(String.valueOf(weight));

        //Birthday input setup
        birthdayInput = (TextInputEditText) view.findViewById(R.id.input_birthday);
        birthdayInput.setInputType(InputType.TYPE_NULL);
        TextInputLayout editBirthdayLayout = (TextInputLayout) view.findViewById(R.id.input_birthday_layout);
        editBirthdayLayout.setEndIconOnClickListener(v -> {
            datePicker.show(context.getSupportFragmentManager(), null);
            datePicker.addOnPositiveButtonClickListener(selection -> birthdayInput.setText(datePicker.getHeaderText()));
        });
    }

    private void setupGenderButtons(){
        //Setting gender button
        RadioButton buttonToActivate;
        if(isMale)
            buttonToActivate = maleButton;
        else
            buttonToActivate = femaleButton;
        buttonToActivate.setChecked(true);
        buttonToActivate.setTextColor(Color.WHITE);

        maleButton.setOnClickListener(v -> {
            editor.putBoolean(getString(R.string.genderKey), true).apply();
            maleButton.setTextColor(Color.WHITE);
            femaleButton.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                maleButton.setElevation(10);
                femaleButton.setElevation(0);
            }
        });
        femaleButton.setOnClickListener(v -> {
            editor.putBoolean(getString(R.string.genderKey), false).apply();
            femaleButton.setTextColor(Color.WHITE);
            maleButton.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                femaleButton.setElevation(10);
                maleButton.setElevation(0);
            }
        });
    }


}
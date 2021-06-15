/*This class allows the user to change and save the data about themselves
 * File: AboutMeFragment.java
 * Author: Serdiuk Andrii
 * */


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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class AboutMeFragment extends Fragment {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private AppCompatRadioButton maleButton;
    private AppCompatRadioButton femaleButton;
    private FragmentActivity context;
    private final MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select your birthday").build();
    private boolean isMale;
    private TextInputEditText usernameInput;
    private TextInputEditText heightInput;
    private TextInputEditText weightInput;
    private TextInputEditText birthdayInput;
    private MaterialButton saveButton;

    private static String name, height, weight, birthday;
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static DatabaseReference reference = FirebaseDatabase.getInstance("https://excelme-480f1-default-rtdb.europe-west1.firebasedatabase.app").getReference("users");

    private final String userId = auth.getCurrentUser().getUid();

    @Override
    public void onAttach(@NotNull Activity activity) {
        context = (FragmentActivity) activity;
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

        //Filling in the fields
        usernameInput = (TextInputEditText) view.findViewById(R.id.login_email);
        heightInput = (TextInputEditText) view.findViewById(R.id.input_height);
        weightInput = (TextInputEditText) view.findViewById(R.id.input_weight);
        //Birthday input setup
        birthdayInput = (TextInputEditText) view.findViewById(R.id.input_birthday);
        birthdayInput.setInputType(InputType.TYPE_NULL);
        TextInputLayout editBirthdayLayout = (TextInputLayout) view.findViewById(R.id.input_birthday_layout);
        editBirthdayLayout.setEndIconOnClickListener(v -> {
            datePicker.show(context.getSupportFragmentManager(), null);
            datePicker.addOnPositiveButtonClickListener(selection -> birthdayInput.setText(datePicker.getHeaderText()));
        });


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserData user = snapshot.getValue(UserData.class);
                if (user != null) {
                    name = user.name;
                    height = user.height;
                    weight = user.weight;
                    birthday = user.birthday;
                    usernameInput.setText(name);
                    heightInput.setText(height);
                    weightInput.setText(weight);
                    birthdayInput.setText(birthday);
                } else
                    Toast.makeText(context, "An error occurred downloading your data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void setupGenderButtons() {
        //Setting gender button
        RadioButton buttonToActivate;
        if (isMale)
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
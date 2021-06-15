package com.example.excelme.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.excelme.InputFilterMinMax;
import com.example.excelme.R;
import com.example.excelme.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SpecifyParametersActivity extends AppCompatActivity {


    private final MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select your birthday").build();
    private Button registerButton;
    private TextInputEditText heightInput, weightInput, birthdayInput;
    private static String height, weight, birthday;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_parameters);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        heightInput = findViewById(R.id.register_height);
        weightInput = findViewById(R.id.register_weight);
        birthdayInput = findViewById(R.id.register_birthday);
        heightInput.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "230")});
        weightInput.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "200")});;
        heightInput.setText(height);
        weightInput.setText(weight);
        birthdayInput.setText(birthday);
        birthdayInput.setInputType(InputType.TYPE_CLASS_DATETIME);
        TextInputLayout editBirthdayLayout = findViewById(R.id.register_birthday_layout);
        editBirthdayLayout.setEndIconOnClickListener(v -> {
            datePicker.show(getSupportFragmentManager(), null);
            datePicker.addOnPositiveButtonClickListener(selection -> birthdayInput.setText(datePicker.getHeaderText()));
        });
    }


    private void registerUser() {
        height = heightInput.getText().toString().trim();
        weight = weightInput.getText().toString().trim();
        birthday = birthdayInput.getText().toString().trim();
        if (height.isEmpty()) {
            heightInput.setError("Please, enter your height");
            heightInput.requestFocus();
        } else if (weight.isEmpty()) {
            weightInput.setError("Please, enter your weight");
            weightInput.requestFocus();
        } else if (birthday.isEmpty()) {
            birthdayInput.setError("Please, enter your birthday");
            birthdayInput.requestFocus();
        } else {
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://excelme-480f1-default-rtdb.europe-west1.firebasedatabase.app");
            DatabaseReference myRef = database.getReference("users");

            mAuth.createUserWithEmailAndPassword(NewUserActivity.email, NewUserActivity.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        UserData user = new UserData(NewUserActivity.name, NewUserActivity.email, height, weight, birthday);
                        myRef.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener
                                (
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    mAuth.getCurrentUser().sendEmailVerification();
                                                    Toast.makeText(SpecifyParametersActivity.this, "Please, check your email to verify the account", Toast.LENGTH_SHORT).show();
                                                    height = null;
                                                    weight = null;
                                                    birthday = null;
                                                    NewUserActivity.name = null;
                                                    NewUserActivity.email = null;
                                                    SpecifyParametersActivity.this.startActivity(new Intent(SpecifyParametersActivity.this, LoginActivity.class));
                                                    SpecifyParametersActivity.this.finish();
                                                }
                                                else
                                                    Toast.makeText(SpecifyParametersActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                    } else
                        Toast.makeText(SpecifyParametersActivity.this, "An error occurred, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
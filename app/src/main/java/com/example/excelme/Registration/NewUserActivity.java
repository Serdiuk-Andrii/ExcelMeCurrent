package com.example.excelme.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.example.excelme.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class NewUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText registerName, registerEmail, registerPassword;
    private Button registerButton;

    static String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        mAuth = FirebaseAuth.getInstance();
        initializeViews();

    }


    private void initializeViews() {
        registerName = findViewById(R.id.register_name);
        registerEmail = findViewById(R.id.login_password);
        registerPassword = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_next_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        registerName.setText(name);
        registerEmail.setText(email);
        registerPassword.setText(password);
    }


    private void registerUser() {
        name = registerName.getText().toString().trim();
        email = registerEmail.getText().toString().trim();
        password = registerPassword.getText().toString().trim();
        if (name.isEmpty()) {
            registerName.setError("Please, enter your name");
            registerName.requestFocus();
        } else if (email.isEmpty()) {
            registerEmail.setError("Please, enter your email");
            registerEmail.requestFocus();
        } else if (password.isEmpty()) {
            registerPassword.setError("Please, enter your password");
            registerPassword.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmail.setError("Incorrect email");
            registerEmail.requestFocus();
        } else if (password.length() < 6) {
            registerPassword.setError("Password must consist of at least 6 characters");
            registerPassword.requestFocus();
        } else
            startActivity(new Intent(this, SpecifyParametersActivity.class));
    }


}
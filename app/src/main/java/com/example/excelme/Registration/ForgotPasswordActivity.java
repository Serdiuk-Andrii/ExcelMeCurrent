package com.example.excelme.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.example.excelme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button sendButton;
    TextInputEditText emailInput;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailInput = findViewById(R.id.register_password);
        sendButton = findViewById(R.id.send_reset_password);
        sendButton.setOnClickListener(v -> {
            resendPassword();
        });
        mAuth = FirebaseAuth.getInstance();
    }

    private void resendPassword() {
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Please, enter your email");
            emailInput.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Incorrect email");
            emailInput.requestFocus();
        } else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset the password", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ForgotPasswordActivity.this, "Such an email is not registered in the system", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
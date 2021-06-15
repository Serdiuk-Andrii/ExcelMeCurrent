package com.example.excelme.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.excelme.MainActivity;
import com.example.excelme.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView registerText, resetPassword;
    private TextInputEditText loginEmail, loginPassword;
    private Button loginButton;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            setContentView(R.layout.activity_login);
            registerText = findViewById(R.id.register);
            registerText.setOnClickListener(v -> LoginActivity.this.startActivity(new Intent(LoginActivity.this, NewUserActivity.class)));
            resetPassword = findViewById(R.id.forgot_password);
            resetPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
            loginEmail = findViewById(R.id.login_email);
            loginPassword = findViewById(R.id.login_password);
            loginButton = findViewById(R.id.login_button);
            loginButton.setOnClickListener(v -> loginUser());
        }
    }

    private void loginUser() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        if (email.isEmpty()) {
            loginEmail.setError("Please, enter your email");
            loginEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Incorrect email");
            loginEmail.requestFocus();
        } else if (password.isEmpty()) {
            loginPassword.setError("Please, enter your password");
            loginPassword.requestFocus();
        } else if (password.length() < 6) {
            loginPassword.setError("Too short password");
            loginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (task.isSuccessful()) {
                    if (!user.isEmailVerified()) {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Please, check your email to verify the account", Toast.LENGTH_LONG).show();
                    } else {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                    }
                } else
                    Toast.makeText(LoginActivity.this, "Invalid password or email", Toast.LENGTH_LONG).show();

            });
        }
    }

}
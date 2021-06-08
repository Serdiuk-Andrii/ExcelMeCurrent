package com.example.excelme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;


public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            Toolbar myToolbar = findViewById(R.id.topAppBar);
            setSupportActionBar(myToolbar);
        }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.settings_icon).setOnClickListener(v -> Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.action_global_settingsFragment));
    }

}
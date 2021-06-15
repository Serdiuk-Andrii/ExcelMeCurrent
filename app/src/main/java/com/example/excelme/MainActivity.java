/*This class launches the program after registration
 * File: MainActivity.java
 * Author: Serdiuk Andrii
 * */


package com.example.excelme;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_main);
            Toolbar myToolbar = findViewById(R.id.topAppBar);
            setSupportActionBar(myToolbar);
        }

    @Override
    protected void onStart() {
        super.onStart();
        NavController controller = Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment);
        findViewById(R.id.settings_icon).setOnClickListener(v -> controller.navigate(R.id.action_global_aboutMeFragment));
        }

}
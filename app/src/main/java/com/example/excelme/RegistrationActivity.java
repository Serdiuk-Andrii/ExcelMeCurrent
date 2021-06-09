/*This class starts the registration
 * File: RegistrationActivity.java
 * Author: Serdiuk Andrii
 * */

package com.example.excelme;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class RegistrationActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private static final int NUM_PAGES = 6;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

    }


    public void finishRegistration(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public @NotNull Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new RegistrationNameFragment(RegistrationActivity.this);
                case 1:
                    return new RegistrationChooseGenderFragment(RegistrationActivity.this);
                case 2:
                    return new RegistrationPhysiqueFragment(RegistrationActivity.this);
                case 3:
                    return new RegistrationChooseGoalFragment(RegistrationActivity.this);
                case 4:
                    return new RegistrationChooseActivityLevelFragment(RegistrationActivity.this);
                case 5:
                    return new ResultFragment(RegistrationActivity.this);
                default:
                    return new Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }

    }

}
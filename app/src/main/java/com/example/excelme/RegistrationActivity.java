package com.example.excelme;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

public class RegistrationActivity extends FragmentActivity {

    private static final int NUM_PAGES = 7;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }


    public void finishRegistration(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }


    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public @NotNull Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new StartFragment();
                case 1:
                    return new RegistrationChooseGenderFragment();
                case 2:
                    return new RegistrationNameFragment();
                case 3:
                    return new RegistrationPhysiqueFragment();
                case 4:
                    return new RegistrationChooseGoalFragment();
                case 5:
                    return new RegistrationChooseActivityLevelFragment();
                case 6:
                    return new ResultFragment();
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
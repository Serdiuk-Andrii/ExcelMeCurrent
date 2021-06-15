/*This class defined the sport menu
 * File: SportFragment.java
 * Author: Serdiuk Andrii
 * */



package com.example.excelme.menuFragmetns;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.excelme.MainFragment;
import com.example.excelme.R;
import com.example.excelme.SportChooseLevel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import static com.example.excelme.SportChooseLevel.CurrentTraining.ABS;
import static com.example.excelme.SportChooseLevel.CurrentTraining.ARMS;
import static com.example.excelme.SportChooseLevel.CurrentTraining.FULLBODY;
import static com.example.excelme.SportChooseLevel.CurrentTraining.LEGS;


public class SportFragment extends Fragment {

    NavController controller;
    private BottomNavigationView menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        view.findViewById(R.id.arms).setOnClickListener(new SportFragment.SportNavigator(ARMS));
        view.findViewById(R.id.legs).setOnClickListener(new SportFragment.SportNavigator(LEGS));
        view.findViewById(R.id.abs).setOnClickListener(new SportFragment.SportNavigator(ABS));
        view.findViewById(R.id.full_body).setOnClickListener(new SportFragment.SportNavigator(FULLBODY));
        menu = view.findViewById(R.id.bottom_navigation);
        if(MainFragment.animationOn)
            menu.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
        menu.setSelectedItemId(R.id.page_1);

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.page_1:
                        controller.navigate(R.id.action_global_sportFragment);
                        break;
                    case R.id.page_2:
                        controller.navigate(R.id.action_global_chooseFoodSection);
                        break;
                    case R.id.page_3:
                        controller.navigate(R.id.action_global_habitsFragment);
                        break;
                    case R.id.page_4:
                        controller.navigate(R.id.action_global_sleepFragment);
                    default:
                        return false;
                }


                return true;
            }
        });



    }

    private class SportNavigator implements View.OnClickListener {

        SportChooseLevel.CurrentTraining destination;

        private SportNavigator(SportChooseLevel.CurrentTraining s) {
            destination = s;
        }

        @Override
        public void onClick(View v) {
            SportChooseLevel.currentTraining = destination;
            SportFragment.this.controller.navigate(R.id.action_sportFragment_to_sportChooseLevel);
        }
    }

}
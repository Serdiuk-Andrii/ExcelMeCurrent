/*This class is responsible for depicting the menu where the user can change section insige food menu
 * File: ChooseFoodSection.java
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.excelme.MainFragment;
import com.example.excelme.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class ChooseFoodSection extends Fragment {

    NavController controller;
    private BottomNavigationView menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_food_section, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        view.findViewById(R.id.recipes_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_chooseFoodSection_to_recipesFragment);
            }
        });
        view.findViewById(R.id.fasting_card_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controller.navigate(R.id.action_chooseFoodSection_to_fastingFragment);
            }
        });
        menu = view.findViewById(R.id.bottom_navigation);
        if(MainFragment.animationOn)
            menu.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
        menu.setSelectedItemId(R.id.page_2);


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



}
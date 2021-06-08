package com.example.excelme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;


public class RecipesFragment extends Fragment {

    NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        view.findViewById(R.id.breakfast).setOnClickListener(new RecipeNavigator("breakfast"));
        view.findViewById(R.id.lunch).setOnClickListener(new RecipeNavigator("lunch"));
        view.findViewById(R.id.dinner).setOnClickListener(new RecipeNavigator("dinner"));
        view.findViewById(R.id.snacks).setOnClickListener(new RecipeNavigator("snacks"));
    }

    private class RecipeNavigator implements View.OnClickListener {

        String destination;

        private RecipeNavigator(String s) {
            destination = s;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("section", destination);
            getParentFragmentManager().setFragmentResult("section", bundle);
            RecipesFragment.this.controller.navigate(R.id.action_recipesFragment_to_displayRecipesFragment);
        }
    }
}
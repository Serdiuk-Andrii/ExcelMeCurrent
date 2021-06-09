/*This class defined the behaviour of ingredients tab
 * File: RecipeIngredientsFragment.java
 * Author: Serdiuk Andrii
 * */


package com.example.excelme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class RecipeIngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecipeExpandedActivity activity = (RecipeExpandedActivity) getActivity();
        TextView ingredientsView = view.findViewById(R.id.ingredientsTextView);
        ingredientsView.setText(activity.ingredients);
    }
}
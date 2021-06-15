/*This class is used to show the expanded version of the recipe chosen by the user
 * File: RecipeExpandedActivity.java
 * Author: Serdiuk Andrii
 **/


package com.example.excelme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class RecipeExpandedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecipeIngredientsFragment ingredientsFragment;
    private RecipeCookingFragment cookingFragment;
    private RecipeNutritionFragment nutritionFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
     String ingredients;
     String nutrition;
     String directions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_expanded);

        //toolbar = findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);


        ingredientsFragment = new RecipeIngredientsFragment();
        nutritionFragment = new RecipeNutritionFragment();
        cookingFragment = new RecipeCookingFragment();

        tabLayout.setupWithViewPager(viewPager);
        imageView = findViewById(R.id.recipeImage);

        RecipeViewAdapter adapter = new RecipeViewAdapter(getSupportFragmentManager(), 0);
        adapter.ingredientsFragment = this.ingredientsFragment;
        adapter.nutritionFragment = this.nutritionFragment;
        adapter.cookingFragment = this.cookingFragment;
        viewPager.setAdapter(adapter);

        setRecipeInformation();

    }

    private void setRecipeInformation() {
        //Getting data about the recipe from the previous activity
        String recipeName;
        String downloadUrl;
        Intent intent = getIntent();
        if(intent != null) {
            String[] recipeValues = intent.getStringArrayExtra("recipe");
            recipeName = recipeValues[0];
            if (recipeName != null)
                setTitle(recipeName);
            downloadUrl = recipeValues[1];
            if (downloadUrl != null)
                Glide.with(this).load(downloadUrl).centerCrop().into(imageView);
            ingredients = recipeValues[2];
            nutrition = recipeValues[3];
            directions = recipeValues[4];
        }

    }

    private static class RecipeViewAdapter extends FragmentPagerAdapter {

        private RecipeIngredientsFragment ingredientsFragment;
        private RecipeCookingFragment cookingFragment;
        private RecipeNutritionFragment nutritionFragment;

        public RecipeViewAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return ingredientsFragment;
            else if(position == 1)
                return cookingFragment;
            return nutritionFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0)
                return "Products";
            if(position == 1)
                return "Cooking";
            return "Nutrition";
        }
    }

}
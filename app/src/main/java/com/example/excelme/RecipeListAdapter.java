/*This file defines a RecyclerAdapter for recipes
* File: RecipeListAdapter.java
* Author: Serdiuk Andrii
* */


package com.example.excelme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>{

    private Activity context;
    private List<Recipe> recipes;

    public RecipeListAdapter(Activity context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    public RecipeListAdapter() {}


    //Controlling how items in the recycler view are created
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecipeListAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());
        recipe.getImageUrl().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String s = uri.toString();
                Glide.with(context).load(s)
                        .placeholder(R.drawable.ic_food).into(holder.recipeImage);
                recipe.setDownloadUrl(s);
            }
        });
        holder.recipeImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.recipe_image_animation));
        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.recipe_card_animation));
        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeExpandedActivity.class);
            String[] values = new String[5];
            values[0] = recipe.getName();
            values[1] = recipe.getDownloadUrl();
            values[2] = recipe.getIngredients();
            values[3] = recipe.getNutrition();
            values[4] = recipe.getInstructions();
            intent.putExtra("recipe", values);
            context.startActivity(intent);
        });
        holder.recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeExpandedActivity.class);
                String[] values = new String[5];
                values[0] = recipe.getName();
                values[1] = recipe.getDownloadUrl();
                values[2] = recipe.getIngredients();
                values[3] = recipe.getNutrition();
                values[4] = recipe.getInstructions();
                intent.putExtra("recipe", values);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        notifyDataSetChanged();
    }

    public void addRecipes(List<Recipe> recipesToAdd) {
        recipes.addAll(recipesToAdd);
        notifyDataSetChanged();
    }


    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeName;
        private ImageView recipeImage;

        private CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            card = itemView.findViewById(R.id.card);

        }

    }


}

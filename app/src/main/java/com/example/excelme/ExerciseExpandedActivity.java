/*This class is responsible for displaying an expanded version of the exercise
 * File: ExerciseExpandedActivity.java
 * Author: Serdiuk Andrii
 * */

package com.example.excelme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;

public class ExerciseExpandedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_expanded);
        findViewById(R.id.recipeImage).setAnimation(AnimationUtils.loadAnimation(this, R.anim.recipe_full_image_fall));
    }


}
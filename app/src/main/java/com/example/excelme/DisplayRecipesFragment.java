/*This class is responsible for displaying recipes
 * File: DisplayRecipesFragment.java
 * Author: Serdiuk Andrii
 * */


package com.example.excelme;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class DisplayRecipesFragment extends Fragment {

    public final static byte NUTRITION_LENGTH = 7;
    public final static long TEN_MEGABYTES = 10 * 1024 * 1024;
    private String section;
    private RecyclerView recyclerView;
    private RecipeListAdapter adapter;
    private final List<Recipe> recipes = new CopyOnWriteArrayList<>();
    private Activity context;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private void getRecipesFromDatabase() {
        StorageReference recipesReference = storageRef.child("recipes/" + section);
        StringBuilder builder = new StringBuilder(100);
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            StorageReference ref = recipesReference.child(i + "/recipe.txt");
            ref.getBytes(TEN_MEGABYTES).addOnSuccessListener(
                    new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            String name, ingredients, directions, nutrition;
                            BufferedReader reader =
                                    new BufferedReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)));
                            try {
                                name = reader.readLine();
                                byte n = Byte.parseByte(reader.readLine());
                                for (byte i = 0; i < n; i++)
                                    builder.append(reader.readLine()).append("\n");
                                ingredients = builder.toString();
                                builder.setLength(0);
                                byte k = Byte.parseByte(reader.readLine());
                                for (byte i = 0; i < k; i++)
                                    builder.append(reader.readLine()).append("\n");
                                directions = builder.toString();
                                builder.setLength(0);
                                for (byte i = 0; i < NUTRITION_LENGTH; i++)
                                    builder.append(reader.readLine()).append("\n");
                                nutrition = builder.toString();
                                adapter.addRecipe(new Recipe(name, recipesReference.child(finalI + "/image.jpg"),
                                        ingredients, directions, nutrition));
                            } catch (IOException | NumberFormatException ex) {
                                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getParentFragmentManager().setFragmentResultListener("section" ,this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                section = result.getString("section");
                //Filling in the list
                getRecipesFromDatabase();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        context = getActivity();
        adapter = new RecipeListAdapter(context, recipes);
        recyclerView = view.findViewById(R.id.recipes_recycler_view);
    }


}
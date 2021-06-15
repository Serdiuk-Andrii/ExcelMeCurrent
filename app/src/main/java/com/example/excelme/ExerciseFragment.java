/*This class is responsible for fetching data about exercises from cloud
*File: ExerciseFragment.java
* Author: Serdiuk Andrii
**/

package com.example.excelme;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ExerciseFragment extends Fragment {

    private Activity context;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private NavController controller;
    private String section; //arms/easy

    private Button startWorkout;
    private RecyclerView recyclerView;
    private ExerciseRecyclerView adapter;
    private List<Exercise> list = new CopyOnWriteArrayList<>();

    public ExerciseFragment() {
        // Required empty public constructor
    }


    private void getDataFromCloud() {
        StringBuilder builder = new StringBuilder(100);
        StorageReference reference = storageRef.child(section);
        for (int i = 1; i < 9; i++) {
            StorageReference tempRef = reference.child(i + "/Exercise.txt");
            int finalI = i;
            tempRef.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    String name, description;
                    int quantity;
                    BufferedReader reader =
                            new BufferedReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)));
                    try {
                        name = reader.readLine();
                        byte n = Byte.parseByte(reader.readLine());
                        for (byte i = 0; i < n; i++)
                            builder.append(reader.readLine()).append("\n");
                        description = builder.toString();
                        quantity = Integer.parseInt(reader.readLine());
                        adapter.add(new Exercise(name, description, quantity, reference.child(finalI +
                                "/Image.gif")));
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        getParentFragmentManager().setFragmentResultListener("section", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                section = result.getString("section");
                getDataFromCloud();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("exercises");
        context = getActivity();
        recyclerView = view.findViewById(R.id.exercisesRecyclerView);
        startWorkout = view.findViewById(R.id.startWorkout);
        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String[] names = new String[list.size()];
                for (int i = 0; i < names.length; i++)
                    names[i] = list.get(i).getName() + " (x" + list.get(i).getQuantity() + ")";
                bundle.putStringArray("names", names);
                String[] urls = new String[list.size()];
                for (int i = 0; i < names.length; i++)
                    urls[i] = list.get(i).getDownloadUrl();
                bundle.putStringArray("urls", urls);
                getParentFragmentManager().setFragmentResult("exercises", bundle);
                controller.navigate(R.id.action_exerciseFragment_to_workoutFragment);
            }
        });
        adapter = new ExerciseRecyclerView(context, list);

    }

    private int getAmount() {
        Toast.makeText(context, section, Toast.LENGTH_SHORT).show();
        return 2;
        /*String complexity = section.substring(section.indexOf("/"));
        switch (complexity) {
            case SportChooseLevel.COMPLEXITY_EASY:
                return SportChooseLevel.COMPLEXITY_EASY_AMOUNT;
            case SportChooseLevel.COMPLEXITY_MEDIUM:
                return SportChooseLevel.COMPLEXITY_MEDIUM_AMOUNT;
            default:
                return SportChooseLevel.COMPLEXITY_HARD_AMOUNT;
        }*/
    }

}
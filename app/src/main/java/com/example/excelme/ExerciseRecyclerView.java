package com.example.excelme;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseRecyclerView extends RecyclerView.Adapter<ExerciseRecyclerView.ViewHolder> {

    private List<Exercise> list;
    private Context context;

    public ExerciseRecyclerView(Context context, List<Exercise> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExerciseRecyclerView.ViewHolder holder, int position) {
        Exercise exercise = list.get(position);
        holder.exerciseButton.setText(exercise.getName()  + "\nx" + String.valueOf(exercise.getQuantity()));
        exercise.getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String s = uri.toString();
                Glide.with(context).asGif().load(s)
                        .into(holder.exerciseImage);
                exercise.setDownloadUrl(s);
            }
        });
        holder.exerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Exercise exercise) {
        list.add(exercise);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView exerciseCard;
        private ImageView exerciseImage;
        private Button exerciseButton;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.exerciseCard = itemView.findViewById(R.id.exerciseCard);
            this.exerciseImage = itemView.findViewById(R.id.exerciseImage);
            this.exerciseButton = itemView.findViewById(R.id.exerciseButton);
        }
    }

}

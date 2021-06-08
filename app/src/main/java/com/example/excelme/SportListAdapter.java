package com.example.excelme;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class SportListAdapter
        extends RecyclerView.Adapter<SportListAdapter.SportViewHolder> {
    private final LinkedList<Exercise> mExerciseList;
    private final LayoutInflater mInflater;
    private final OnSportListener mOnSportListener;



    public SportListAdapter(Context context, LinkedList<Exercise> exercises, OnSportListener onSportListener){
        mInflater = LayoutInflater.from(context);
        this.mExerciseList =exercises;
        this.mOnSportListener = onSportListener;
    }


    @NonNull

    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View mItemView = mInflater.inflate(R.layout.sportlist_item,
                null);
        return new SportViewHolder(mItemView, this, mOnSportListener);*/
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        Exercise mCurrent = mExerciseList.get(position);
        holder.titleItemView.setText(mCurrent.geteExercise());
        holder.timeItemView.setText(String.valueOf(mCurrent.geteTime()));
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

    class SportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView titleItemView;
        public final TextView timeItemView;
        final SportListAdapter mAdapter;
        OnSportListener onSportListener;

        public SportViewHolder(View itemView, SportListAdapter adapter, OnSportListener onSportListener){
            super(itemView);
            //set all the elements in aim to fill them lately
            titleItemView = itemView.findViewById(R.id.title);
            timeItemView = itemView.findViewById(R.id.time);
            this.mAdapter=adapter;
            this.onSportListener = onSportListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSportListener.onExerciseClick(getAdapterPosition());
        }
    }

    public interface OnSportListener {
        void onExerciseClick(int position);
    }
}
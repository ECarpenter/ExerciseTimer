package com.example.exercisetimer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.exercisetimer.Adapters.ExerciseListAdapter;
import com.example.exercisetimer.R;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
    private String[] exerciseNames;
    private int currPosition;

    public ExerciseListAdapter(String[] list) {
        exerciseNames = list;
        currPosition = 0;
    }

    public void setFocus(int position){

        currPosition = position;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public ExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        TextView textItem = item.findViewById(R.id.exerciseItem);
        return new ExerciseListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListViewHolder holder, int position) {
        holder.textView.setText(exerciseNames[position]);
        if (position == currPosition) {
            holder.textView.setBackgroundColor(holder.textView.getContext().getResources().getColor(R.color.Green));
            holder.textView.setTextSize((float) (holder.textView.getTextSize() * 1.5));
        }
    }

    @Override
    public int getItemCount() {
        return exerciseNames.length;
    }


    public static class ExerciseListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ExerciseListViewHolder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.exerciseItem);
        }
    }
}

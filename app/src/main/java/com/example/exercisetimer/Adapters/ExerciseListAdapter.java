package com.example.exercisetimer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.exercisetimer.R;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
    private String[] exerciseNames;
    private int[] exerciseTimes;
    private int currPosition = 0;
    private boolean running = false;

    public ExerciseListAdapter(String[] exerciseNames, int[] exerciseTimes) {
        this.exerciseNames = exerciseNames;
        this.exerciseTimes = exerciseTimes;
    }

    public void setPosition(int position){
        currPosition = position;
        notifyDataSetChanged();
    }

    public int getTime(){
        return exerciseTimes[currPosition];
    }

    public void startRunning() {
        this.running = true;
        notifyDataSetChanged();
    }

    public void stopRunning(){
        this.running = false;
        currPosition = 0;
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
        String temp = exerciseNames[position];

        if (position == currPosition && running) {
                holder.textView.setBackgroundColor(ContextCompat.getColor(holder.textView.getContext(),R.color.Green));
                holder.textView.setTextSize((float) (holder.textView.getTextSize() * 1.5));
        }
        else {
            temp += " - " + holder.textView.getResources().getString(R.string.clock_display, exerciseTimes[position]/60, exerciseTimes[position]%60);
        }
        holder.textView.setText(temp);
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

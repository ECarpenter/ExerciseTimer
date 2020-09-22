package com.example.exercisetimer;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClockAndControlFragment extends Fragment {

    private TextView clockDisplay;
    private Handler clockHandler;
    private RecyclerView exerciseList;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private long startTime;


    public static class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {
        private String[] exerciseNames;

        public ExerciseListAdapter(String[] list) {
            exerciseNames = list;
        }

        @NonNull
        @Override
        public ExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
            TextView textItem = (TextView)item.findViewById(R.id.exerciseItem);
            return new ExerciseListViewHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseListViewHolder holder, int position) {
            holder.textView.setText(exerciseNames[position]);
        }

        @Override
        public int getItemCount() {
            return exerciseNames.length;
        }

        public static class ExerciseListViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ExerciseListViewHolder(@NonNull View itemView) {
                super(itemView);


                textView = (TextView)itemView.findViewById(R.id.exerciseItem);
            }
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.clock_and_control, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        clockDisplay = (TextView) view.findViewById(R.id.clock_display);
        clockHandler = new Handler();
        exerciseList = (RecyclerView) view.findViewById(R.id.ExerciseListView);

        exerciseList.setHasFixedSize(true);
        listLayoutManager = new LinearLayoutManager(getContext());
        exerciseList.setLayoutManager(listLayoutManager);

        String[] tempList = getResources().getStringArray(R.array.exercise_names);
        listAdapter = new ExerciseListAdapter(tempList);
        exerciseList.setAdapter(listAdapter);

        clockDisplay.setText(getString(R.string.clock_display,0,0,0));


        class clockRunnable implements Runnable {
            private Handler clockHandler;
            private TextView clockDisplay;
            private boolean running;
            private long startTime;
            private long elapsedTime;
            private long elapsedSeconds;
            private long elapsedMinutes;
            private long elapsedHours;


            public clockRunnable(Handler clockHandler, long startTime, TextView clockDisplay){
                this.clockHandler = clockHandler;
                this.clockDisplay = clockDisplay;
                this.running = false;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                if (this.running) {
                    this.clockHandler.postDelayed(this, 500);
                    this.elapsedTime = System.currentTimeMillis() - this.startTime;
                    this.elapsedSeconds = this.elapsedTime / 1000;
                    this.elapsedMinutes = this.elapsedSeconds / 60;
                    this.elapsedHours = this.elapsedMinutes / 60;
                    this.clockDisplay.setText(getString(R.string.clock_display,elapsedHours,elapsedMinutes%60,elapsedSeconds%60));
                }
            }
        }

        final clockRunnable clockRunner = new clockRunnable(clockHandler, startTime, clockDisplay);


        view.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = System.currentTimeMillis();
                clockRunner.setStartTime(startTime);
                clockRunner.setRunning(true);
                clockHandler.post(clockRunner);
            }
        });

        view.findViewById(R.id.button_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockRunner.setRunning(false);
            }
        });
    }
}
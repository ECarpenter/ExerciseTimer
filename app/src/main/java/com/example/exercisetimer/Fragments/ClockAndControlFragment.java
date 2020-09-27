package com.example.exercisetimer.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercisetimer.Adapters.ExerciseListAdapter;
import com.example.exercisetimer.Ativities.MainActivity;
import com.example.exercisetimer.R;
import com.example.exercisetimer.Runnables.clockRunnable;

public class ClockAndControlFragment extends Fragment {

    private TextView clockDisplay;
    private Handler clockHandler;
    private long startTime;
    private RecyclerView exerciseList;
    private ExerciseListAdapter exerciseListAdapter;
    private RecyclerView.LayoutManager listLayoutManager;


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


        //Sets up the clock display
        clockDisplay = view.findViewById(R.id.clock_display);
        clockHandler = new Handler(Looper.myLooper());
        clockDisplay.setText(getString(R.string.clock_display, 0, 0));


        //Set up Recycler view for exercise list at the bottom
        exerciseList = view.findViewById(R.id.ExerciseListView);
        exerciseList.setHasFixedSize(true);
        listLayoutManager = new LinearLayoutManager(exerciseList.getContext());
        exerciseList.setLayoutManager(listLayoutManager);
        String[] nameList = getResources().getStringArray(R.array.exercise_names);
        int[] timeList = getResources().getIntArray(R.array.exercise_times);
        exerciseListAdapter = new ExerciseListAdapter(nameList, timeList);
        exerciseList.setAdapter(exerciseListAdapter);

        //create timer
        final clockRunnable clockRunner = new clockRunnable(clockHandler, startTime, clockDisplay, exerciseList);


        view.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clockRunner.startRunning();
                exerciseListAdapter.startRunning();
            }
        });

        view.findViewById(R.id.button_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockRunner.stopRunning();
                exerciseListAdapter.stopRunning();
            }
        });
    }
}
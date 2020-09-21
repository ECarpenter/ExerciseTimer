package com.example.exercisetimer;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long[] startTime = {System.currentTimeMillis()};

        final TextView clockDisplay = (TextView) view.findViewById(R.id.clock_display);
        final Handler clockHandler = new Handler();
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
                //this.startTime = startTime;
                this.clockDisplay = clockDisplay;
                this.running = false;
                //this.elapsedTime = System.currentTimeMillis() - this.startTime;
                //this.elapsedSeconds = this.elapsedTime/1000;
                //this.elapsedMinutes = this.elapsedSeconds/60;
                //this.elapsedHours = this.elapsedMinutes/60;
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

        final clockRunnable clockRunner = new clockRunnable(clockHandler, startTime[0], clockDisplay);


        view.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime[0] = System.currentTimeMillis();
                clockRunner.setStartTime(startTime[0]);
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
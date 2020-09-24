package com.example.exercisetimer.Runnables;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exercisetimer.Adapters.ExerciseListAdapter;
import com.example.exercisetimer.R;

public class clockRunnable implements Runnable {
    private Handler clockHandler;
    private TextView clockDisplay;
    private RecyclerView exerciseList;
    private ExerciseListAdapter exerciseListAdapter;
    private ToneGenerator alarm;
    private boolean running;
    private int position;
    private long startTime;
    private long elapsedTime;
    private long elapsedSeconds;
    private long elapsedMinutes;


    public clockRunnable(Handler clockHandler, long startTime, TextView clockDisplay, RecyclerView exerciseList){
        this.clockHandler = clockHandler;
        this.clockDisplay = clockDisplay;
        this.exerciseList = exerciseList;
        this.alarm = new ToneGenerator(AudioManager.STREAM_ALARM,100);
        position = 0;
        exerciseListAdapter = (ExerciseListAdapter) exerciseList.getAdapter();
        if (exerciseListAdapter != null) {
            exerciseListAdapter.setFocus(position);
        }
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
            this.clockHandler.postDelayed(this, 1000);
            this.elapsedTime = System.currentTimeMillis() - this.startTime;
            this.elapsedSeconds = this.elapsedTime / 1000;
            this.elapsedMinutes = this.elapsedSeconds / 60;
            this.clockDisplay.setText(clockDisplay.getResources().getString(R.string.clock_display,elapsedMinutes%60,elapsedSeconds%60));
            if (elapsedSeconds%5 == 0){
                if (position != (elapsedSeconds/5)%7){
                    position = (int)(elapsedSeconds/5)%7;
                    exerciseListAdapter.setFocus(position);
                }
                alarm.startTone(ToneGenerator.TONE_PROP_BEEP2,50);
            }
        }
    }
}

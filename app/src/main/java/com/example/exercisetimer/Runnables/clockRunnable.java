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
    private long displaySeconds;


    public clockRunnable(Handler clockHandler, long startTime, TextView clockDisplay, RecyclerView exerciseList){
        this.clockHandler = clockHandler;
        this.clockDisplay = clockDisplay;
        this.exerciseList = exerciseList;
        this.alarm = new ToneGenerator(AudioManager.STREAM_ALARM,100);
        position = 0;
        exerciseListAdapter = (ExerciseListAdapter) exerciseList.getAdapter();
        if (exerciseListAdapter != null) {
            exerciseListAdapter.setPosition(position);
        }
        this.running = false;
    }

    public void startRunning() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
        this.clockHandler.post(this);
    }

    public void stopRunning(){
        this.running = false;
    }


    @Override
    public void run() {
        if (this.running) {
            this.clockHandler.postDelayed(this, 500);
            this.elapsedTime = System.currentTimeMillis() - this.startTime;
            this.elapsedSeconds = this.elapsedTime / 1000;
            this.displaySeconds = exerciseListAdapter.getTime() - this.elapsedSeconds;
            this.clockDisplay.setText(clockDisplay.getResources().getString(R.string.clock_display,displaySeconds/60,displaySeconds%60));
            if (elapsedSeconds >= exerciseListAdapter.getTime()){
                position++;
                this.startTime = System.currentTimeMillis();
                alarm.startTone(ToneGenerator.TONE_PROP_BEEP2,50);
                if (position < exerciseListAdapter.getItemCount()){
                    exerciseListAdapter.setPosition(position);
                }
                else {
                    this.running = false;
                }


            }
        }
    }
}

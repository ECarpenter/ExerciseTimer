package com.example.exercisetimer.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.exercisetimer.R;


@Database(entities = {Workout.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WorkoutDao workoutDao();
    private static AppDatabase Instance;

    static AppDatabase getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, context.getResources().getString(R.string.database_name)).build();
                }
            }
        }
        return Instance;
    }
}


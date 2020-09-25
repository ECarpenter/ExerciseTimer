package com.example.exercisetimer.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Workout {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "exercise_name")
    public String exerciseName;
}

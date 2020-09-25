package com.example.exercisetimer.Data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workout")
    List<Workout> getAll();
}

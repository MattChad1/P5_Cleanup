package com.cleanup.todoc.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cleanup.todoc.model.TasksWithProject;

import java.util.List;

@Dao
public interface TasksWithProjectDao {

    @Transaction
    @Query("SELECT * FROM Task")
    LiveData<List<TasksWithProject>> getTasksWithProject();



}

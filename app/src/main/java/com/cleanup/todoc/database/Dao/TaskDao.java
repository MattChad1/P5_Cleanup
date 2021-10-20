package com.cleanup.todoc.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.ProjectWithTasks;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Insert
    void insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE idTask = :id")
    int deleteTask(long id);

    @Transaction
    @Query("SELECT * FROM Project")
    LiveData<List<ProjectWithTasks>> getTaskWithProject();
}

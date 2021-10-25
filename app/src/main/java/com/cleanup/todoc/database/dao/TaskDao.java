package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cleanup.todoc.model.ProjectWithTasks;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Insert
    long insertTask(Task task);

    @Query("DELETE FROM task WHERE idTask = :idTask")
    void deleteTask(long idTask);

    @Transaction
    @Query("SELECT * FROM Project")
    LiveData<List<ProjectWithTasks>> getProjectWithTasks();
}

package com.cleanup.todoc.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasks(long projectId);

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    int deleteTask(long id);
}

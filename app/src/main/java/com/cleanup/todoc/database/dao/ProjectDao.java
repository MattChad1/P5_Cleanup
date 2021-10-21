package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project WHERE id = :id")
    LiveData<Project> getProjectById(long id);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();

    @Insert
    long insertProject(Project project);

    @Update
    int updateProject(Project project);

    @Query("DELETE FROM Project WHERE id = :id")
    int deleteProject(long id);
}


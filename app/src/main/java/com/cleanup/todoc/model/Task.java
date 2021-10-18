package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.cleanup.todoc.database.Dao.ProjectDao;

import java.util.Comparator;

@Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    // clé commune
    private long projectId;

    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;

    public long creationTimestamp;

    public Task(long id, long projectId, @NonNull String name) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp();
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    private void setId(long id) {
        this.id = id;
    }

    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }

//    @Nullable
//    public Project getProject() {
//        return ProjectDao.getProject(projectId);
//    }

    @NonNull
    public String getName() {
        return name;
    }

    private void setName(@NonNull String name) {
        this.name = name;
    }

    private void setCreationTimestamp() {
        this.creationTimestamp = System.currentTimeMillis()/1000;
    }


}

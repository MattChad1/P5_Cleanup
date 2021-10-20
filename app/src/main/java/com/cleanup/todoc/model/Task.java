package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long idTask;

    // clé commune
    private long projectId;

    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String nameTask;

    public long creationTimestamp;

    public Task(long idTask, long projectId, @NonNull String nameTask) {
        this.setIdTask(idTask);
        this.setProjectId(projectId);
        this.setNameTask(nameTask);
        this.setCreationTimestamp();
    }

    public long getIdTask() {
        return idTask;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    private void setIdTask(long idTask) {
        this.idTask = idTask;
    }

    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @NonNull
    public String getNameTask() {
        return nameTask;
    }

    private void setNameTask(@NonNull String nameTask) {
        this.nameTask = nameTask;
    }

    private void setCreationTimestamp() {
        this.creationTimestamp = System.currentTimeMillis() / 1000;
    }

    @Override
    public String toString() {
        return "Task{" + "idTask=" + idTask + ", projectId=" + projectId + ", nameTask='" + nameTask + '\'' + ", creationTimestamp=" + creationTimestamp + '}';
    }
}

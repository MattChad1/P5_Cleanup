package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long idTask;

    // clé commune
    private long projectId;

    private String nameTask;

    public long creationTimestamp;

    public Task(long idTask, long projectId, @NonNull String nameTask) {
        this.setIdTask(idTask);
        this.setProjectId(projectId);
        this.setNameTask(nameTask);
        this.setCreationTimestamp();
    }

    @Ignore
    public Task(long idTask, long projectId, @NonNull String nameTask, long creationTimestamp) {
        this.setIdTask(idTask);
        this.setProjectId(projectId);
        this.setNameTask(nameTask);
        this.setCreationTimestamp(creationTimestamp);
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

    private void setCreationTimestamp(long timestamp) {
        this.creationTimestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Task{" + "idTask=" + idTask + ", projectId=" + projectId + ", nameTask='" + nameTask + '\'' + ", creationTimestamp=" + creationTimestamp + '}';
    }
}

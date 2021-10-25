package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProjectWithTasks {
    @Embedded
    public Project project;
    @Relation(parentColumn = "id", entityColumn = "projectId")
    public List<Task> tasks;

    @NonNull
    @Override
    public String toString() {
        return "ProjectWithTasks{" + "project=" + project + ", tasks=" + tasks + '}';
    }
}

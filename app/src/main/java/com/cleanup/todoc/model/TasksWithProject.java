package com.cleanup.todoc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TasksWithProject {

    @Embedded public Project project;
    @Relation(parentColumn = "id", entityColumn = "projectId")
    public Task task;

}

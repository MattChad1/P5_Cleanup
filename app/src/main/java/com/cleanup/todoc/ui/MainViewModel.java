package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.Repository;

import java.util.List;

public class MainViewModel extends ViewModel {

    @NonNull
    private final Repository repository;
    private final LiveData<List<Project>> projects;

    public MainViewModel (@NonNull Repository repository) {
        this.repository = repository;
        projects = repository.getAllProjects();
    }

    LiveData<List<Project>> getAllProjects() { return projects; }

    public void insert(Project project) { repository.insert(project); }


}
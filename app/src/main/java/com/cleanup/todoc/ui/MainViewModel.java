package com.cleanup.todoc.ui;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.Repository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private final LiveData<List<Project>> projects;

    public MainViewModel (Application application, Repository repository) {
        repository = new Repository(application);
        projects = repository.getProjects();
    }

    LiveData<List<Project>> getAllProjects() { return projects; }

    public void insert(Project project) { repository.insert(project); }
}
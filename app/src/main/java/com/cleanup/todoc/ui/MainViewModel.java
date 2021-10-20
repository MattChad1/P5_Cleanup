package com.cleanup.todoc.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.ProjectWithTasks;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    String TAG = "Log ViewModel";

    @NonNull
    private Repository repository;

    private final MutableLiveData<List<TaskViewStateItem>> tasksLD = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> allProjects = new MutableLiveData<>();

    public MainViewModel (@NonNull Repository repository) {
        this.repository = repository;
        Log.i(TAG, "MainViewModel: ");
    }

    public LiveData<List<TaskViewStateItem>> getAllTasks() {
        return Transformations.map(repository.getTaskWithProject(), allProjectsWithTasks -> {
            List<TaskViewStateItem> liststateitems = new ArrayList<>();
            if (allProjectsWithTasks != null) {
                for (ProjectWithTasks pwt : allProjectsWithTasks) {
                    if (pwt.tasks != null) {
                        for (Task t : pwt.tasks) {
                            liststateitems.add(new TaskViewStateItem(t.getIdTask(), t.getNameTask(), pwt.project.getName(), pwt.project.getColor(), t.getCreationTimestamp()));
                        }
                    }
                }
            }
            return liststateitems;
        });
    }

    public void insertTask(Task task) { repository.insertTask(task); }
    public void deleteTask(Task task) { repository.deleteTask(task); }





    public LiveData<List<Project>> getAllProjects() {
        return repository.getAllProjects();
    }
}
package com.cleanup.todoc.ui;

import android.app.Application;
import android.util.Log;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TasksWithProject;
import com.cleanup.todoc.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    String TAG = "Log ViewModel";

    @NonNull
    private final Repository repository;
    private final MutableLiveData<List<MainViewStateItem>> tasksLD = new MutableLiveData<>();
    public LiveData<List<Project>> allProjects;

    public MainViewModel (@NonNull Repository repository) {
        this.repository = repository;
        Log.i(TAG, "MainViewModel: ");
    }

//     public LiveData<List<MainViewStateItem>> getAllTasks() {
//        return Transformations.map(repository.getAllTasks(), tasks -> {
//            List<MainViewStateItem> listTasks = new ArrayList<>();
//            for (Task t: tasks) {
//
//                listTasks.add(new MainViewStateItem(t.getId(), t.getName(), "Test", 0xFFA3CED2, 5L));
//                //List<Project> project = repository.getProjectById(t.getProjectId()).getValue();
//                List<Project> projects = repository.getProjectById(t.getProjectId()).getValue();
//                if (projects!=null) {
//                    Log.i(TAG, "getAllTasks22222: " + projects.toString());
//                    listTasks.add(new MainViewStateItem(t.getId(), t.getName(), projects.get(0).getName(), projects.get(0).getColor(), t.getCreationTimestamp()));
//                }
//            }
//            //tasksLD.setValue(listTasks);
//            Log.i("Log MainViewModel", "getAllTasks: " + listTasks.toString());
//            return listTasks;
//        });
//    }

    public LiveData<List<MainViewStateItem>> getAllTasks() {
        return Transformations.map(repository.getTasksWithProjects(), tps -> {
            List<MainViewStateItem> listTasks = new ArrayList<>();
            for (TasksWithProject t: tps) {
                listTasks.add(new MainViewStateItem(t.task.getId(), t.task.getName(), t.project.getName(), t.project.getColor(), t.task.getCreationTimestamp()));

            }
            //tasksLD.setValue(listTasks);
            Log.i("Log MainViewModel", "getAllTasks: " + listTasks.toString());
            return listTasks;
        });
    }

    public void insert(Task task) { repository.insertTask(task); }

    public LiveData<List<Project>> getAllProjects() {
        return repository.getAllProjects();
    }
}
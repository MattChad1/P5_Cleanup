package com.cleanup.todoc.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.ProjectWithTasks;

import java.util.List;

public class Repository {

    private final ProjectDao projectDao;
    private final TaskDao taskDao;
    private CleanupDatabase db;

    public Repository(Context context) {
        db = CleanupDatabase.getInstance(context);
        projectDao = db.projectDao();
        taskDao = db.taskDao();
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }

    public void insert(Project project) {
        CleanupDatabase.databaseWriteExecutor.execute(() -> {
            projectDao.insertProject(project);
        });
    }

    public LiveData<List<Task>> getAllTasks() {return taskDao.getAllTasks();}

    public void insertTask(Task task) {
        CleanupDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(task);
        });
    }

    public void deleteTask(long taskId) {
        CleanupDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteTask(taskId);
        });
    }

    public LiveData<Project> getProjectById(long id) {
        return projectDao.getProjectById(id);
    }

    public LiveData<List<ProjectWithTasks>> getProjectWithTasks() {
        return taskDao.getProjectWithTasks();
    }
}
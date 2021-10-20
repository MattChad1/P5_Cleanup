package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.database.Dao.ProjectDao;
import com.cleanup.todoc.database.Dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.ProjectWithTasks;

import java.util.List;

public class Repository {

    private ProjectDao projectDao;
    private TaskDao taskDao;


    public Repository(Application application) {
        CleanupDatabase db = CleanupDatabase.getInstance(application);
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


    public LiveData<List<Project>> getProjectById (long id) {
    return projectDao.getProjectById(id);
    }

    public LiveData<List<ProjectWithTasks>> getTaskWithProject() {
        return taskDao.getTaskWithProject();
    }
}
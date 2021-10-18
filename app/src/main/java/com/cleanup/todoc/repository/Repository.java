package com.cleanup.todoc.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.database.Dao.ProjectDao;
import com.cleanup.todoc.database.Dao.TaskDao;
import com.cleanup.todoc.database.Dao.TasksWithProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TasksWithProject;

import java.util.List;

public class Repository {

    private TaskDao taskDao;
    private ProjectDao projectDao;
    private TasksWithProjectDao tasksWithProjectDao;

    private LiveData<List<Task>> listTasks;
    private LiveData<List<Project>> allProjects;

    public Repository(Application application) {
        CleanupDatabase db = CleanupDatabase.getInstance(application);
        taskDao = db.taskDao();
        projectDao = db.projectDao();
        allProjects = projectDao.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Project project) {
        CleanupDatabase.databaseWriteExecutor.execute(() -> {
            projectDao.insertProject(project);
        });
    }

    public void insertTask(Task task) {
        CleanupDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(task);
        });
    }

    //public LiveData<List<TasksWithProject>> getAllTasks() {return taskDao.getAllTasks();}
    public LiveData<List<Task>> getAllTasks() {return taskDao.getAllTasks();}

    public LiveData<List<Project>> getProjectById (long id) {
    return projectDao.getProject(id);
    }

    public LiveData<List<TasksWithProject>> getTasksWithProjects() {
        Log.i("Log repo", "getTasksWithProjects: ");
        return tasksWithProjectDao.getTasksWithProject();}
}
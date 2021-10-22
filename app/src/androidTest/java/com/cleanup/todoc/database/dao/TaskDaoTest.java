package com.cleanup.todoc.database.dao;

import static org.junit.Assert.assertEquals;

import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.ProjectWithTasks;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.util.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class TaskDaoTest {

    private CleanupDatabase db;
    private ProjectDao projectDao;
    private TaskDao taskDao;

    final Project testProject1 = new Project(0, "Test project", Color.parseColor("#003366"));
    final Project testProject2 = new Project(0, "Test project 2", Color.parseColor("#552288"));
    final Project testProject3 = new Project(0, "Test project 3", Color.parseColor("#112233"));

    final Task task1 = new Task(0, 1, "Task 1 - Project 1");
    final Task task2 = new Task(0, 1, "Task 2 - Project 1");
    final Task task3 = new Task(0, 2, "Task 1 - Project 2");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CleanupDatabase.class).allowMainThreadQueries().build();
        projectDao = db.projectDao();
        taskDao = db.taskDao();

        projectDao.insertProject(testProject1);
        projectDao.insertProject(testProject2);
        projectDao.insertProject(testProject3);

    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }


    @Test
    public void insertTask() throws InterruptedException {
        long idInsert = taskDao.insertTask(task1);
        List<Task> tasksInserted = LiveDataTestUtils.getOrAwaitValue(taskDao.getAllTasks());

        assertEquals(tasksInserted.get(0).getNameTask(), task1.getNameTask());
        assertEquals(tasksInserted.get(0).getProjectId(), task1.getProjectId());

    }

    @Test
    public void getAllTasks() throws InterruptedException {
        taskDao.insertTask(task1);
        taskDao.insertTask(task2);
        taskDao.insertTask(task3);

        List<Task> tasksInserted = LiveDataTestUtils.getOrAwaitValue(taskDao.getAllTasks());
        assertEquals(3, tasksInserted.size());
    }

    @Test
    public void deleteTask() throws InterruptedException {
        long idInsert = taskDao.insertTask(task1);
        taskDao.deleteTask(idInsert);
        List<Task> tasks = LiveDataTestUtils.getOrAwaitValue(taskDao.getAllTasks());
        assertEquals(0, tasks.size());
    }

    @Test
    public void getTasksWithProject() throws InterruptedException {
        taskDao.insertTask(task1);
        taskDao.insertTask(task2);
        taskDao.insertTask(task3);

        List<ProjectWithTasks> projectWithTasks = LiveDataTestUtils.getOrAwaitValue(taskDao.getProjectWithTasks());
        assertEquals(3, projectWithTasks.size()); // Total projects
        assertEquals(2, projectWithTasks.get(0).tasks.size()); // Total tasks in project 1 (id = 1)
        assertEquals(1, projectWithTasks.get(1).tasks.size()); // Total tasks in project 2 (id = 2)


    }

}

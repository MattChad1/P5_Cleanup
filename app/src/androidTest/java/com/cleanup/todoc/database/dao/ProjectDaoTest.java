package com.cleanup.todoc.database.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Dao;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.util.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private CleanupDatabase db;
    private ProjectDao projectDao;
    private TaskDao taskDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CleanupDatabase.class).allowMainThreadQueries().build();
        projectDao = db.projectDao();
        taskDao = db.taskDao();
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void getProjectByIdTest() throws InterruptedException {
        final Task task1 = new Task(1, 1, "task 1");
        final Task task2 = new Task(2, 2, "task 2");
        final Task task3 = new Task(3, 3, "task 3");
        final Task task4 = new Task(4, 4, "task 4");

        assertEquals("Projet Tartampion", ((Project) LiveDataTestUtils.getOrAwaitValue(projectDao.getProjectById(task1.getProjectId()))).getName());
//        assertEquals("Projet Lucidia", (projectDao.getProjectById(task2.getProjectId()).getValue()).getName());
//        assertEquals("Projet Circus", (projectDao.getProjectById(task3.getProjectId()).getValue()).getName());
        assertNull(projectDao.getProjectById(task1.getProjectId()));


    }

    @Test
    public void insertProjectTest() throws InterruptedException {
        final Project testProject = new Project(0, "Test project", -25000);

        long idInsert = projectDao.insertProject(testProject);
        Project projetInserted = LiveDataTestUtils.getOrAwaitValue(projectDao.getProjectById(idInsert));

        assertEquals(projetInserted.getName(), testProject.getName());
        assertEquals(projetInserted.getColor(), testProject.getColor());

    }
}

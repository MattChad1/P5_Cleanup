package com.cleanup.todoc.database.dao;

import static org.junit.Assert.assertEquals;

import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.util.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class ProjectDaoTest {

    private CleanupDatabase db;
    private ProjectDao projectDao;

    final Project testProject1 = new Project(0, "Test project", Color.parseColor("#003366"));
    final Project testProject2 = new Project(0, "Test project 2", Color.parseColor("#552288"));
    final Project testProject3 = new Project(0, "Test project 3", Color.parseColor("#112233"));

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CleanupDatabase.class).allowMainThreadQueries().build();
        projectDao = db.projectDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertProject() throws InterruptedException {
        long idInsert = projectDao.insertProject(testProject1);
        Project projetInserted = LiveDataTestUtils.getOrAwaitValue(projectDao.getProjectById(idInsert));

        assertEquals(projetInserted.getName(), testProject1.getName());
        assertEquals(projetInserted.getColor(), testProject1.getColor());
    }

    @Test
    public void getAllProjects() throws InterruptedException {
        projectDao.insertProject(testProject1);
        projectDao.insertProject(testProject2);
        projectDao.insertProject(testProject3);

        List<Project> projetsInserted = LiveDataTestUtils.getOrAwaitValue(projectDao.getAllProjects());
        assertEquals(3, projetsInserted.size());
    }
}

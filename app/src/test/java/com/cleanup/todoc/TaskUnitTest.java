package com.cleanup.todoc;

import com.cleanup.todoc.database.CleanupDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.Repository;
import com.cleanup.todoc.ui.MainViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */

@RunWith(MockitoJUnitRunner.class)
public class TaskUnitTest {

    @Mock
    private Application application;

    //Context context = ApplicationProvider.getApplicationContext();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private Repository repository;

    private CleanupDatabase db;

    @Before
    public void setUp() {
    //application = MyApplication.getInstance();

        repository = new Repository(application.getApplicationContext());
        MainViewModel vm = new MainViewModel(repository);



    }

    @Test
    public void test_projects() {
        final Task task1 = new Task(1, 1, "task 1");
        final Task task2 = new Task(2, 2, "task 2");
        final Task task3 = new Task(3, 3, "task 3");
        final Task task4 = new Task(4, 4, "task 4");

        assertEquals("Projet Tartampion", ((Project) repository.getProjectById(task1.getProjectId()).getValue()).getName());
        assertEquals("Projet Lucidia", ((Project) repository.getProjectById(task2.getProjectId()).getValue()).getName());
        assertEquals("Projet Circus", ((Project) repository.getProjectById(task3.getProjectId()).getValue()).getName());
        assertNull(repository.getProjectById(task1.getProjectId()));
    }

//    @Test
//    public void test_az_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskAZComparator());
//
//        assertSame(tasks.get(0), task1);
//        assertSame(tasks.get(1), task3);
//        assertSame(tasks.get(2), task2);
//    }

//    @Test
//    public void test_za_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskZAComparator());
//
//        assertSame(tasks.get(0), task2);
//        assertSame(tasks.get(1), task3);
//        assertSame(tasks.get(2), task1);
//    }
//
//    @Test
//    public void test_recent_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskRecentComparator());
//
//        assertSame(tasks.get(0), task3);
//        assertSame(tasks.get(1), task2);
//        assertSame(tasks.get(2), task1);
//    }
//
//    @Test
//    public void test_old_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskOldComparator());
//
//        assertSame(tasks.get(0), task1);
//        assertSame(tasks.get(1), task2);
//        assertSame(tasks.get(2), task3);
//    }
}
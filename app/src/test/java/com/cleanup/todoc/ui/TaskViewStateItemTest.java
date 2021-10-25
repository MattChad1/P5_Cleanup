package com.cleanup.todoc.ui;

import static org.junit.Assert.*;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskViewStateItemTest {

    final TaskViewStateItem taskA = new TaskViewStateItem(0, "Task a", "Projet 1", -5000, 10000);
    final TaskViewStateItem taskZ = new TaskViewStateItem(0, "Task z", "Projet 2", -1000, 20000);
    final TaskViewStateItem taskC = new TaskViewStateItem(0, "Task c", "Projet 2", -1000, 30000);
    final List<TaskViewStateItem> tasks = new ArrayList<>(Arrays.asList(taskZ, taskA, taskC));


    @Test
    public void TaskComparator_fromOldToRecent () {
        List<TaskViewStateItem> tasksExpected = new ArrayList<>(Arrays.asList(taskA, taskZ, taskC));
        Collections.sort(tasks, new TaskViewStateItem.TaskOldComparator());
        assertEquals(tasksExpected, tasks);
    }

    @Test
    public void TaskComparator_fromRecentToOld () {
        List<TaskViewStateItem> tasksExpected = new ArrayList<>(Arrays.asList(taskC, taskZ, taskA));
        Collections.sort(tasks, new TaskViewStateItem.TaskRecentComparator());
        assertEquals(tasksExpected, tasks);
    }

    @Test
    public void TaskComparator_AZ () {
        List<TaskViewStateItem> tasksExpected = new ArrayList<>(Arrays.asList(taskA, taskC, taskZ));
        Collections.sort(tasks, new TaskViewStateItem.TaskAZComparator());
        assertEquals(tasksExpected, tasks);
    }

    @Test
    public void TaskComparator_ZA () {
        List<TaskViewStateItem> tasksExpected = new ArrayList<>(Arrays.asList(taskZ, taskC, taskA));
        Collections.sort(tasks, new TaskViewStateItem.TaskZAComparator());
        assertEquals(tasksExpected, tasks);
    }



}
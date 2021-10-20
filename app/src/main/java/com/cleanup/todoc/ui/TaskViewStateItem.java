package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class TaskViewStateItem {

    private long taskId;
    private String nameTask;
    private String projectName;
    private int colorIcon;
    private long creationTimestamp;

    public TaskViewStateItem(long taskId, String nameTask, String projectName, int colorIcon, long creationTimestamp) {
        this.taskId = taskId;
        this.nameTask = nameTask;
        this.projectName = projectName;
        this.colorIcon = colorIcon;
        this.creationTimestamp = creationTimestamp;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getColorIcon() {
        return colorIcon;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<TaskViewStateItem> {
        @Override
        public int compare(TaskViewStateItem left, TaskViewStateItem right) {
            return left.getNameTask().compareTo(right.getNameTask());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<TaskViewStateItem> {
        @Override
        public int compare(TaskViewStateItem left, TaskViewStateItem right) {
            return right.getNameTask().compareTo(left.getNameTask());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<TaskViewStateItem> {
        @Override
        public int compare(TaskViewStateItem left, TaskViewStateItem right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<TaskViewStateItem> {
        @Override
        public int compare(TaskViewStateItem left, TaskViewStateItem right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "MainViewStateItem{" + "taskId=" + taskId + ", nameTask='" + nameTask + '\'' + ", projectName='" + projectName + '\'' + ", colorIcon"
                + "=" + colorIcon + ", creationTimestamp=" + creationTimestamp + '}';
    }
}

package com.cleanup.todoc.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.ProjectWithTasks;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel extends ViewModel {

    String TAG = "Log ViewModel";

    @NonNull
    private final Repository repository;

    private final MutableLiveData<List<TaskViewStateItem>> allTasksMutableLiveData = new MutableLiveData<>();
    private final MediatorLiveData<List<TaskViewStateItem>> tasksToDisplayMediatorLD = new MediatorLiveData<>();
    private final MutableLiveData<SortMethod> sortMethodMutableLiveData = new MutableLiveData<>();

    public MainViewModel (@NonNull Repository repository) {
        this.repository = repository;
        Log.i(TAG, "MainViewModel: ");

        tasksToDisplayMediatorLD.addSource(getAllTasks(), tasksToDisplayMediatorLD::setValue);
        tasksToDisplayMediatorLD.addSource(getSortMethodMutableLiveData(), new Observer<SortMethod>() {
            @Override
            public void onChanged(SortMethod sortMethod) {
                List<TaskViewStateItem> taskViewStateItems = allTasksMutableLiveData.getValue();
                if (taskViewStateItems!=null) {
                switch (sortMethod) {
                    case ALPHABETICAL:
                        Collections.sort(taskViewStateItems, new TaskViewStateItem.TaskAZComparator());
                        break;
                    case ALPHABETICAL_INVERTED:
                        Collections.sort(taskViewStateItems, new TaskViewStateItem.TaskZAComparator());
                        break;
                    case RECENT_FIRST:
                        Collections.sort(taskViewStateItems, new TaskViewStateItem.TaskRecentComparator());
                        break;
                    case OLD_FIRST:
                        Collections.sort(taskViewStateItems, new TaskViewStateItem.TaskOldComparator());
                        break;
                }
                tasksToDisplayMediatorLD.setValue(taskViewStateItems);


            }
        }
    });
    }

    public LiveData<List<TaskViewStateItem>> getAllTasks() {
        return Transformations.map(repository.getTaskWithProject(), allProjectsWithTasks -> {
            List<TaskViewStateItem> liststateitems = new ArrayList<>();
            if (allProjectsWithTasks != null) {
                for (ProjectWithTasks pwt : allProjectsWithTasks) {
                    if (pwt.tasks != null) {
                        for (Task t : pwt.tasks) {
                            liststateitems.add(new TaskViewStateItem(t.getIdTask(), t.getNameTask(), pwt.project.getName(), pwt.project.getColor(), t.getCreationTimestamp()));
                        }
                    }
                }
            }
            allTasksMutableLiveData.setValue(liststateitems);
            return liststateitems;
        });
    }

    public void insertTask(Task task) { repository.insertTask(task); }
    public void deleteTask(long taskId) { repository.deleteTask(taskId); }
    public LiveData<List<Project>> getAllProjects() {
        return repository.getAllProjects();
    }

    public void updateSortMethod(SortMethod sortMethod) {sortMethodMutableLiveData.setValue(sortMethod);}

    public MutableLiveData<SortMethod> getSortMethodMutableLiveData() {return sortMethodMutableLiveData;}

    public MediatorLiveData<List<TaskViewStateItem>> getTasksToDisplayMediatorLD() {
        return tasksToDisplayMediatorLD;
    }

    /**
     * List of all possible sort methods for task
     */
    public enum SortMethod {
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        RECENT_FIRST,
        OLD_FIRST,
        NONE
    }

}
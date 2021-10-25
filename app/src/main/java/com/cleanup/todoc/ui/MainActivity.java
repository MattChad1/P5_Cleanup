package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.BuildConfig;
import com.cleanup.todoc.R;
import com.cleanup.todoc.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    private String TAG = "Log MainActivity";

    private MainViewModel vm;

    private List<Project> allProjects = new ArrayList();

    @NonNull
    private List<TaskViewStateItem> tasks = new ArrayList<>();

    private TasksAdapter adapter;

    /** Dialog to create a new task */
    @Nullable
    public AlertDialog dialog = null;

    /** EditText that allows user to set the name of a task */
    @Nullable
    private EditText dialogEditText = null;

    /** Spinner that allows the user to associate a project to a task */
    @Nullable
    private Spinner dialogSpinner = null;

    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /** The TextView displaying the empty state */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);
        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        adapter = new TasksAdapter(tasks, this);
        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);

        vm.getTasksToDisplayMediatorLD().observe(this, taskViewStateItems -> {
            if (BuildConfig.DEBUG) Log.i(TAG, "onCreate: MAJ tasks");
            tasks.clear();
            tasks.addAll(taskViewStateItems);
            if (tasks.size() == 0) {
                lblNoTasks.setVisibility(View.VISIBLE);
                listTasks.setVisibility(View.GONE);
            } else {
                lblNoTasks.setVisibility(View.GONE);
                listTasks.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();
        });
        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            vm.updateSortMethod(MainViewModel.SortMethod.ALPHABETICAL);
        } else if (id == R.id.filter_alphabetical_inverted) {
            vm.updateSortMethod(MainViewModel.SortMethod.ALPHABETICAL_INVERTED);
        } else if (id == R.id.filter_oldest_first) {
            vm.updateSortMethod(MainViewModel.SortMethod.OLD_FIRST);
        } else if (id == R.id.filter_recent_first) {
            vm.updateSortMethod(MainViewModel.SortMethod.RECENT_FIRST);
        }
        return true;
    }

    @Override
    public void onDeleteTask(long taskId) {
        vm.deleteTask(taskId);
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            String taskName = dialogEditText.getText().toString();
            Project projectSelected = (Project) dialogSpinner.getSelectedItem();

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else {

                Task task = new Task(
                        0,
                        projectSelected.getId(),
                        taskName
                );

                vm.insertTask(task);
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();
        dialog.show();
        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);
        populateDialogSpinner();
    }


    /**
     * Returns the dialog allowing the user to create a new task.
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter2 = new ArrayAdapter<Project>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vm.getAllProjects().observe(this, projects -> {
            allProjects.clear();
            allProjects.addAll(projects);
            adapter2.notifyDataSetChanged();
        });

        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter2);
        }
    }




}

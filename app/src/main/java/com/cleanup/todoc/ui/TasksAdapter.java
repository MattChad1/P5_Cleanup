package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    @NonNull
    private List<TaskViewStateItem> tasks;
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    TasksAdapter(@NonNull final List<TaskViewStateItem> tasks, @NonNull final DeleteTaskListener deleteTaskListener) {
        this.tasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull final List<TaskViewStateItem> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public interface DeleteTaskListener {
        void onDeleteTask(long taskId);
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imgProject; // The circle icon showing the color of the project
        private final TextView lblTaskName;
        private final TextView lblProjectName;
        private final AppCompatImageView imgDelete;

        /** The listener for when a task needs to be deleted */
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);
            this.deleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(v -> {
                final  Object tag = v.getTag();
                TaskViewHolder.this.deleteTaskListener.onDeleteTask((long) tag);
            });
        }

        /**
         * Binds a taskViewStateItem to the item view.
         * @param taskViewStateItem the task to bind in the item view
         */
        void bind(TaskViewStateItem taskViewStateItem) {
            lblTaskName.setText(taskViewStateItem.getNameTask());
            imgDelete.setTag(taskViewStateItem.getTaskId());
            lblProjectName.setText(taskViewStateItem.getProjectName());
            imgProject.setSupportImageTintList(ColorStateList.valueOf(taskViewStateItem.getColorIcon()));
        }
    }
}

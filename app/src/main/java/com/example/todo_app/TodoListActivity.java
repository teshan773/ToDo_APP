package com.example.todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private MaterialButton addTaskButton;
    private ImageButton settingsButton;
    private TaskAdapter taskAdapter;
    private TaskManager taskManager;
    private UserManager userManager;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        userManager = new UserManager(this);
        taskManager = new TaskManager(this);

        // Check if user is logged in
        if (!userManager.isLoggedIn()) {
            navigateToSignIn();
            return;
        }

        currentUserId = userManager.getCurrentUserId();
        if (currentUserId <= 0) {
            navigateToSignIn();
            return;
        }

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        addTaskButton = findViewById(R.id.addTaskButton);
        settingsButton = findViewById(R.id.settingsButton);

        // Set up RecyclerView
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTasks();

        addTaskButton.setOnClickListener(v -> {
            AddTaskDialogFragment dialogFragment = new AddTaskDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AddTaskDialog");
        });

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(TodoListActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void loadTasks() {
        List<Task> tasks = taskManager.getUserTasks(currentUserId);
        taskAdapter = new TaskAdapter(tasks, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onEditClick(Task task) {
                // Show edit dialog
                EditTaskDialogFragment dialogFragment = new EditTaskDialogFragment(task);
                dialogFragment.show(getSupportFragmentManager(), "EditTaskDialog");
            }

            @Override
            public void onDeleteClick(Task task) {
                showDeleteConfirmationDialog(task);
            }
        });
        taskRecyclerView.setAdapter(taskAdapter);
    }

    private void showDeleteConfirmationDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this task?")
                .setPositiveButton("Delete", (dialog, id) -> {
                    taskManager.deleteTask(task.getId(), currentUserId);
                    loadTasks();
                    Toast.makeText(TodoListActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    public void addNewTask(String taskName, String taskDate, String taskTime) {
        boolean success = taskManager.addTask(currentUserId, taskName, taskDate, taskTime);
        if (success) {
            loadTasks();
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTask(int taskId, String taskName, String taskDate, String taskTime) {
        boolean success = taskManager.updateTask(taskId, currentUserId, taskName, taskDate, taskTime);
        if (success) {
            loadTasks();
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToSignIn() {
        Intent intent = new Intent(TodoListActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!userManager.isLoggedIn()) {
            navigateToSignIn();
            return;
        }
        currentUserId = userManager.getCurrentUserId();
        loadTasks();
    }
}

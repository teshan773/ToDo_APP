package com.example.todo_app;

import android.content.Context;

import java.util.List;

public class TaskManager {

    private DBHelper dbHelper;

    public TaskManager(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    /**
     * Add a new task for a user
     */
    public boolean addTask(int userId, String taskName, String taskDate, String taskTime) {
        if (taskName.isEmpty() || taskDate.isEmpty() || taskTime.isEmpty()) {
            return false;
        }
        return dbHelper.insertTask(taskName, taskDate, taskTime, userId) != -1;
    }

    /**
     * Get all tasks for a user
     */
    public List<Task> getUserTasks(int userId) {
        return dbHelper.getTaskListByUser(userId);
    }

    /**
     * Update a task
     */
    public boolean updateTask(int taskId, int userId, String taskName, String taskDate, String taskTime) {
        if (taskName.isEmpty() || taskDate.isEmpty() || taskTime.isEmpty()) {
            return false;
        }
        return dbHelper.updateTask(taskId, taskName, taskDate, taskTime, userId) > 0;
    }

    /**
     * Delete a task
     */
    public boolean deleteTask(int taskId, int userId) {
        return dbHelper.deleteTask(taskId, userId) > 0;
    }
}

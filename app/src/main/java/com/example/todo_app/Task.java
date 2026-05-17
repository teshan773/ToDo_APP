package com.example.todo_app;

public class Task {
    private int id;
    private int userId;
    private String taskName;
    private String taskDate;
    private String taskTime;

    public Task(int id, int userId, String taskName, String taskDate, String taskTime) {
        this.id = id;
        this.userId = userId;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}

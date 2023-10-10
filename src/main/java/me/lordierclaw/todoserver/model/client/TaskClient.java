package me.lordierclaw.todoserver.model.client;

import me.lordierclaw.todoserver.model.base.Identifiable;
import me.lordierclaw.todoserver.model.base.Task;

import java.sql.Timestamp;

public class TaskClient extends Identifiable {
    private boolean status;
    private String name;
    private Integer categoryId;
    private Timestamp dueDate;
    private Integer reminderAt;
    private String repeatType;
    private String notes;

    public static TaskClient fromTask(Task task) {
        TaskClient taskClient = new TaskClient();
        taskClient.setId(task.getId());
        taskClient.setStatus(task.getStatus());
        taskClient.setName(task.getName());
        taskClient.setCategoryId(task.getCategoryId());
        taskClient.setReminderAt(task.getReminderAt());
        taskClient.setRepeatType(task.getRepeatType());
        taskClient.setNotes(task.getNotes());
        return taskClient;
    }

    public Task toTask(int userId) {
        Task task = new Task();
        task.setId(this.id);
        task.setStatus(this.status);
        task.setName(this.name);
        task.setCategoryId(this.categoryId);
        task.setReminderAt(this.reminderAt);
        task.setRepeatType(this.repeatType);
        task.setNotes(this.notes);
        task.setUserId(userId);
        return task;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getReminderAt() {
        return reminderAt;
    }

    public void setReminderAt(Integer reminderAt) {
        this.reminderAt = reminderAt;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

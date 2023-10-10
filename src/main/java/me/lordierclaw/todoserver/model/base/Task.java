package me.lordierclaw.todoserver.model.base;

import java.sql.Timestamp;

public class Task extends Identifiable{
    private boolean status;
    private String name;
    private Integer userId;
    private Integer categoryId;
    private Timestamp dueDate;
    private Integer reminderAt;
    private String repeatType;
    private String notes;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

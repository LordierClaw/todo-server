package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.repository.ITaskRepository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

public class TaskRepository implements ITaskRepository {
    @Inject
    private AbstractDatabaseInstance databaseInstance;

    @Override
    public int insertTask(Task task) {
        return databaseInstance.getTaskDao().insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        databaseInstance.getTaskDao().updateTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        databaseInstance.getTaskDao().deleteTask(task);
    }

    @Override
    public void deleteTask(int id) {
        databaseInstance.getTaskDao().deleteTask(id);
    }

    @Override
    public Task getTask(int id) {
        return databaseInstance.getTaskDao().getTask(id);
    }

    @Override
    public List<Task> getAllTaskOfUser(int userId) {
        return databaseInstance.getTaskDao().getAllTaskOfUser(userId);
    }

    @Override
    public List<Task> getAllTaskInCategory(int categoryId) {
        return databaseInstance.getTaskDao().getAllTaskInCategory(categoryId);
    }

    @Override
    public List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) {
        return databaseInstance.getTaskDao().getAllTaskOfUserContainsTitle(userId, keyword);
    }

    @Override
    public List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) {
        return databaseInstance.getTaskDao().getAllTaskOfUserInRangeTime(userId, startTime, endTime);
    }

    @Override
    public List<Task> getTaskCountByStatusOfUser(int userId, boolean status) {
        return databaseInstance.getTaskDao().getTaskCountByStatusOfUser(userId, status);
    }

    @Override
    public List<CategoryCount> getCategoryCountsOfUser(int userId) {
        return databaseInstance.getTaskDao().getCategoryCountsOfUser(userId);
    }
}

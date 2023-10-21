package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.ITaskRepository;

import java.sql.Timestamp;
import java.util.List;

public class TaskRepository extends AbstractRepository implements ITaskRepository {
    @Override
    public int insertTask(Task task) {
        int result = databaseInstance.getTaskDao().insertTask(task);
        invalidate();
        return result;
    }

    @Override
    public boolean updateTask(Task task) {
        boolean result = databaseInstance.getTaskDao().updateTask(task);
        if (result) invalidate();
        return result;
    }

    @Override
    public boolean deleteTask(Task task) {
        boolean result = databaseInstance.getTaskDao().deleteTask(task);
        if (result) invalidate();
        return result;
    }

    @Override
    public Task getTask(int userId, int id) {
        return databaseInstance.getTaskDao().getTask(userId, id);
    }

    @Override
    public List<Task> getAllTaskOfUser(int userId) {
        return databaseInstance.getTaskDao().getAllTaskOfUser(userId);
    }

    @Override
    public List<Task> getAllTaskInCategory(int userId, int categoryId) {
        return databaseInstance.getTaskDao().getAllTaskInCategory(userId, categoryId);
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

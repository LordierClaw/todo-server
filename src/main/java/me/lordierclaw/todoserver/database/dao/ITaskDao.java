package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.base.Task;

import java.sql.Timestamp;
import java.util.List;

public interface ITaskDao {
    int insertTask(Task task);
    void updateTask(Task task);
    void deleteTask(Task task);
    void deleteTask(int id);
    Task getTask(int id);
    List<Task> getAllTaskOfUser(int userId);
    List<Task> getAllTaskInCategory(int categoryId);
    List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword);
    List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime);
    List<Task> getTaskCountByStatusOfUser(int userId, boolean status);
    List<CategoryCount> getCategoryCountsOfUser(int userId);
}

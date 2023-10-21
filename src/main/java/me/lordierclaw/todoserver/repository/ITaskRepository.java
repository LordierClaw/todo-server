package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.base.Task;

import java.sql.Timestamp;
import java.util.List;

public interface ITaskRepository {
    int insertTask(Task task);
    boolean updateTask(Task task);
    boolean deleteTask(Task task);
    Task getTask(int userId, int id);
    List<Task> getAllTaskOfUser(int userId);
    List<Task> getAllTaskInCategory(int userId, int categoryId);
    List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword);
    List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime);
    List<Task> getTaskCountByStatusOfUser(int userId, boolean status);
    List<CategoryCount> getCategoryCountsOfUser(int userId);
}

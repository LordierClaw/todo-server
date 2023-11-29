package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.dto.CategoryCountDto;
import me.lordierclaw.todoserver.model.base.Task;

import java.sql.Timestamp;
import java.util.List;

public interface ITaskDao {
    Integer insertTask(Task task);
    Boolean updateTask(Task task);
    Boolean deleteTask(Task task);
    Task getTask(int userId, int id);
    List<Task> getAllTaskOfUser(int userId);
    List<Task> getAllTaskInCategory(int userId, int categoryId);
    List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword);
    List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime);
    List<Task> getTaskCountByStatusOfUser(int userId, boolean status);
    List<CategoryCountDto> getCategoryCountsOfUser(int userId);
}

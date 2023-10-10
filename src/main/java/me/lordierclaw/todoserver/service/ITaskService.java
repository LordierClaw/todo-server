package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.client.TaskClient;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import java.sql.Timestamp;
import java.util.List;

public interface ITaskService {
    int insertTask(String token, TaskClient taskClient) throws UnauthorizedException;
    void updateTask(String token, TaskClient taskClient) throws UnauthorizedException;
    void deleteTask(String token, TaskClient taskClient) throws UnauthorizedException;
    void deleteTask(String token, int id) throws UnauthorizedException;
    TaskClient getTask(String token, int id) throws UnauthorizedException;
    List<TaskClient> getAllTaskOfUser(String token) throws UnauthorizedException;
    List<TaskClient> getAllTaskInCategory(String token, int categoryId);
    List<TaskClient> getAllTaskOfUserContainsTitle(String token, String keyword) throws UnauthorizedException;
    List<TaskClient> getAllTaskOfUserInRangeTime(String token, Timestamp startTime, Timestamp endTime) throws UnauthorizedException;
    List<TaskClient> getTaskCountByStatusOfUser(String token, boolean status) throws UnauthorizedException;
    List<CategoryCount> getCategoryCountsOfUser(String token);
}

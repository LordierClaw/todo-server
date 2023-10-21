package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.client.TaskClient;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import java.util.List;

public interface ITaskService {
    int insertTask(String token, TaskClient taskClient) throws UnauthorizedException;
    boolean updateTask(String token, TaskClient taskClient) throws UnauthorizedException;
    boolean deleteTask(String token, TaskClient taskClient) throws UnauthorizedException;
    TaskClient getTask(String token, int id) throws UnauthorizedException;
    List<TaskClient> getAllTaskOfUser(String token) throws UnauthorizedException;
    List<TaskClient> getAllTaskInCategory(String token, int categoryId) throws UnauthorizedException;
    List<TaskClient> getAllTaskOfUserContainsTitle(String token, String keyword) throws UnauthorizedException;
    List<TaskClient> getAllTaskOfUserInRangeTime(String token, long startTimestamp, long endTimestamp) throws UnauthorizedException;
    List<TaskClient> getTaskCountByStatusOfUser(String token, boolean status) throws UnauthorizedException;
    List<CategoryCount> getCategoryCountsOfUser(String token) throws UnauthorizedException;
}

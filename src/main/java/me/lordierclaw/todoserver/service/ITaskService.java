package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.model.dto.CategoryCountDto;
import me.lordierclaw.todoserver.model.dto.TaskDto;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import java.util.List;

public interface ITaskService {
    int insertTask(String token, TaskDto taskDto) throws UnauthorizedException;
    boolean updateTask(String token, TaskDto taskDto) throws UnauthorizedException;
    boolean deleteTask(String token, TaskDto taskDto) throws UnauthorizedException;
    TaskDto getTask(String token, int id) throws UnauthorizedException;
    List<TaskDto> getAllTaskOfUser(String token) throws UnauthorizedException;
    List<TaskDto> getAllTaskInCategory(String token, int categoryId) throws UnauthorizedException;
    List<TaskDto> getAllTaskOfUserContainsTitle(String token, String keyword) throws UnauthorizedException;
    List<TaskDto> getAllTaskOfUserInRangeTime(String token, long startTimestamp, long endTimestamp) throws UnauthorizedException;
    List<TaskDto> getTaskCountByStatusOfUser(String token, boolean status) throws UnauthorizedException;
    List<CategoryCountDto> getCategoryCountsOfUser(String token) throws UnauthorizedException;
}

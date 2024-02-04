package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;
import me.lordierclaw.todoserver.model.dto.TaskDto;

import java.util.List;

public interface ITaskService {
    int insertTask(String token, TaskDto taskDto) throws ResponseException;

    void updateTask(String token, TaskDto taskDto) throws ResponseException;

    void deleteTask(String token, TaskDto taskDto) throws ResponseException;

    TaskDto getTask(String token, int id) throws ResponseException;

    List<TaskDto> getAllTaskOfUser(String token) throws ResponseException;

    List<TaskDto> getAllTaskInCategory(String token, int categoryId) throws ResponseException;

    List<TaskDto> getAllTaskOfUserContainsTitle(String token, String keyword) throws ResponseException;

    List<TaskDto> getAllTaskOfUserInRangeTime(String token, long startTimestamp, long endTimestamp) throws ResponseException;

    List<TaskDto> getTaskCountByStatusOfUser(String token, boolean status) throws ResponseException;

    List<CategoryCountDto> getCategoryCountsOfUser(String token) throws ResponseException;
}

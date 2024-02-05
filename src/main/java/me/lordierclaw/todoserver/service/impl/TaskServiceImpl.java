package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;
import me.lordierclaw.todoserver.model.dto.TaskDto;
import me.lordierclaw.todoserver.repository.TaskRepository;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.TaskService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl extends AuthorizedService implements TaskService {

    @Inject
    private TaskRepository taskRepository;

    private List<TaskDto> mapDtoList(List<Task> tasks) throws ResponseException {
        if (tasks == null) throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
        List<TaskDto> results = new ArrayList<>();
        for (Task task : tasks) {
            results.add(TaskDto.fromTask(task));
        }
        return results;
    }

    @Override
    public int insertTask(String token, TaskDto taskDto) throws ResponseException {
        Task task = taskDto.toTask(authorizeUser(token));
        try {
            return taskRepository.insertTask(task);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTask(String token, TaskDto taskDto) throws ResponseException {
        Task task = taskDto.toTask(authorizeUser(token));
        try {
            taskRepository.updateTask(task);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTask(String token, TaskDto taskDto) throws ResponseException {
        Task task = taskDto.toTask(authorizeUser(token));
        try {
            taskRepository.deleteTask(task);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TaskDto getTask(String token, int id) throws ResponseException {
        try {
            Task task = taskRepository.getTask(authorizeUser(token), id);
            if (task == null) {
                throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
            }
            return TaskDto.fromTask(task);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<TaskDto> getAllTaskOfUser(String token) throws ResponseException {
        try {
            return mapDtoList(taskRepository.getAllTaskOfUser(authorizeUser(token)));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<TaskDto> getAllTaskInCategory(String token, int categoryId) throws ResponseException {
        try {
            return mapDtoList(taskRepository.getAllTaskInCategory(authorizeUser(token), categoryId));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<TaskDto> getAllTaskOfUserContainsTitle(String token, String keyword) throws ResponseException {
        int userId = authorizeUser(token);
        try {
            return mapDtoList(taskRepository.getAllTaskOfUserContainsTitle(userId, keyword));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<TaskDto> getAllTaskOfUserInRangeTime(String token, long startTimestamp, long endTimestamp) throws ResponseException {
        int userId = authorizeUser(token);
        Timestamp startTime = new Timestamp(startTimestamp);
        Timestamp endTime = new Timestamp(endTimestamp);
        try {
            return mapDtoList(taskRepository.getAllTaskOfUserInRangeTime(userId, startTime, endTime));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<TaskDto> getTaskCountByStatusOfUser(String token, boolean status) throws ResponseException {
        int userId = authorizeUser(token);
        try {
            return mapDtoList(taskRepository.getTaskCountByStatusOfUser(userId, status));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<CategoryCountDto> getCategoryCountsOfUser(String token) throws ResponseException {
        try {
            return taskRepository.getCategoryCountsOfUser(authorizeUser(token));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}

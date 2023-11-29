package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;
import me.lordierclaw.todoserver.model.dto.TaskDto;
import me.lordierclaw.todoserver.repository.ITaskRepository;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.ITaskService;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskService extends AuthorizedService implements ITaskService {

    @Inject
    private ITaskRepository taskRepository;

    private List<TaskDto> mapTaskClientList(List<Task> tasks) {
        if (tasks == null) return null;
        List<TaskDto> results = new ArrayList<>();
        for(Task task: tasks) {
            results.add(TaskDto.fromTask(task));
        }
        return results;
    }

    @Override
    public int insertTask(String token, TaskDto taskDto) throws UnauthorizedException {
        Task task = taskDto.toTask(authorizeUser(token));
        return taskRepository.insertTask(task);
    }

    @Override
    public boolean updateTask(String token, TaskDto taskDto) throws UnauthorizedException {
        Task task = taskDto.toTask(authorizeUser(token));
        return taskRepository.updateTask(task);
    }

    @Override
    public boolean deleteTask(String token, TaskDto taskDto) throws UnauthorizedException {
        Task task = taskDto.toTask(authorizeUser(token));
        return taskRepository.deleteTask(task);
    }

    @Override
    public TaskDto getTask(String token, int id) throws UnauthorizedException {
        return TaskDto.fromTask(taskRepository.getTask(authorizeUser(token), id));
    }

    @Override
    public List<TaskDto> getAllTaskOfUser(String token) throws UnauthorizedException {
        return mapTaskClientList(taskRepository.getAllTaskOfUser(authorizeUser(token)));
    }

    @Override
    public List<TaskDto> getAllTaskInCategory(String token, int categoryId) throws UnauthorizedException {
        return mapTaskClientList(taskRepository.getAllTaskInCategory(authorizeUser(token), categoryId));
    }

    @Override
    public List<TaskDto> getAllTaskOfUserContainsTitle(String token, String keyword) throws UnauthorizedException {
        int userId = authorizeUser(token);
        return mapTaskClientList(taskRepository.getAllTaskOfUserContainsTitle(userId, keyword));
    }

    @Override
    public List<TaskDto> getAllTaskOfUserInRangeTime(String token, long startTimestamp, long endTimestamp) throws UnauthorizedException {
        int userId = authorizeUser(token);
        Timestamp startTime = new Timestamp(startTimestamp);
        Timestamp endTime = new Timestamp(endTimestamp);
        return mapTaskClientList(taskRepository.getAllTaskOfUserInRangeTime(userId, startTime, endTime));
    }

    @Override
    public List<TaskDto> getTaskCountByStatusOfUser(String token, boolean status) throws UnauthorizedException {
        int userId = authorizeUser(token);
        return mapTaskClientList(taskRepository.getTaskCountByStatusOfUser(userId, status));
    }

    @Override
    public List<CategoryCountDto> getCategoryCountsOfUser(String token) throws UnauthorizedException {
        return taskRepository.getCategoryCountsOfUser(authorizeUser(token));
    }
}

package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.client.TaskClient;
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

    private List<TaskClient> mapTaskClientList(List<Task> tasks) {
        if (tasks == null) return null;
        List<TaskClient> results = new ArrayList<>();
        for(Task task: tasks) {
            results.add(TaskClient.fromTask(task));
        }
        return results;
    }

    @Override
    public int insertTask(String token, TaskClient taskClient) throws UnauthorizedException {
        Task task = taskClient.toTask(authorizeUser(token));
        return taskRepository.insertTask(task);
    }

    @Override
    public boolean updateTask(String token, TaskClient taskClient) throws UnauthorizedException {
        Task task = taskClient.toTask(authorizeUser(token));
        return taskRepository.updateTask(task);
    }

    @Override
    public boolean deleteTask(String token, TaskClient taskClient) throws UnauthorizedException {
        Task task = taskClient.toTask(authorizeUser(token));
        return taskRepository.deleteTask(task);
    }

    @Override
    public TaskClient getTask(String token, int id) throws UnauthorizedException {
        return TaskClient.fromTask(taskRepository.getTask(authorizeUser(token), id));
    }

    @Override
    public List<TaskClient> getAllTaskOfUser(String token) throws UnauthorizedException {
        return mapTaskClientList(taskRepository.getAllTaskOfUser(authorizeUser(token)));
    }

    @Override
    public List<TaskClient> getAllTaskInCategory(String token, int categoryId) throws UnauthorizedException {
        return mapTaskClientList(taskRepository.getAllTaskInCategory(authorizeUser(token), categoryId));
    }

    @Override
    public List<TaskClient> getAllTaskOfUserContainsTitle(String token, String keyword) throws UnauthorizedException {
        int userId = authorizeUser(token);
        return mapTaskClientList(taskRepository.getAllTaskOfUserContainsTitle(userId, keyword));
    }

    @Override
    public List<TaskClient> getAllTaskOfUserInRangeTime(String token, long startTimestamp, long endTimestamp) throws UnauthorizedException {
        int userId = authorizeUser(token);
        Timestamp startTime = new Timestamp(startTimestamp);
        Timestamp endTime = new Timestamp(endTimestamp);
        return mapTaskClientList(taskRepository.getAllTaskOfUserInRangeTime(userId, startTime, endTime));
    }

    @Override
    public List<TaskClient> getTaskCountByStatusOfUser(String token, boolean status) throws UnauthorizedException {
        int userId = authorizeUser(token);
        return mapTaskClientList(taskRepository.getTaskCountByStatusOfUser(userId, status));
    }

    @Override
    public List<CategoryCount> getCategoryCountsOfUser(String token) throws UnauthorizedException {
        return taskRepository.getCategoryCountsOfUser(authorizeUser(token));
    }
}

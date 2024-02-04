package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;

import java.sql.Timestamp;
import java.util.List;

public interface ITaskRepository {
    int insertTask(Task task) throws DataCrudException, DataInvalidateException;

    void updateTask(Task task) throws DataCrudException, DataInvalidateException;

    void deleteTask(Task task) throws DataCrudException, DataInvalidateException;

    Task getTask(int userId, int id) throws DataCrudException;

    List<Task> getAllTaskOfUser(int userId) throws DataCrudException;

    List<Task> getAllTaskInCategory(int userId, int categoryId) throws DataCrudException;

    List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) throws DataCrudException;

    List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) throws DataCrudException;

    List<Task> getTaskCountByStatusOfUser(int userId, boolean status) throws DataCrudException;

    List<CategoryCountDto> getCategoryCountsOfUser(int userId) throws DataCrudException;
}

package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;

import java.sql.Timestamp;
import java.util.List;

public interface TaskDao {
    boolean isTaskBelongToUser(int userId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    int insertTask(Task task) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    void updateTask(Task task) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    void deleteTask(Task task) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    Task getTask(int userId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Task> getAllTaskOfUser(int userId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Task> getAllTaskInCategory(int userId, int categoryId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Task> getTaskCountByStatusOfUser(int userId, boolean status) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<CategoryCountDto> getCategoryCountsOfUser(int userId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;
}

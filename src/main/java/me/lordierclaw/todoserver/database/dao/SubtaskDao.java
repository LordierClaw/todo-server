package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public interface SubtaskDao {
    boolean isSubtaskBelongToUser(int userId, int taskId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    int insertSubtask(Subtask subtask) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    void updateSubtask(Subtask subtask) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    void deleteSubtask(Subtask subtask) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    Subtask getSubtask(int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Subtask> getAllSubtaskOfTask(int taskId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;
}

package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.SubtaskDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.SingleValueMapper;
import me.lordierclaw.todoserver.database.utils.mapper.impl.SubtaskMapper;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutorBuilder;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public class SubtaskDaoImpl extends AbstractDao implements SubtaskDao {
    public SubtaskDaoImpl(QueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public boolean isSubtaskBelongToUser(int userId, int taskId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT COUNT(*) FROM subtask WHERE id = ? AND task_id IN (SELECT id FROM task WHERE id = ? AND user_id = ?)";
        List<Integer> list = prepareExecutor().query(sql, new SingleValueMapper<>(Integer.class), id, taskId, userId);
        return list != null && !list.isEmpty() && list.get(0) != 0;
    }

    @Override
    public int insertSubtask(Subtask subtask) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "INSERT INTO subtask (status, name, task_id) " +
                "VALUES (?, ?, ?)";
        return prepareExecutor().insert(sql, subtask.getStatus(), subtask.getName(), subtask.getTaskId());
    }

    @Override
    public void updateSubtask(Subtask subtask) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "UPDATE subtask SET status = ?, name = ? WHERE id = ?";
        prepareExecutor().update(sql, subtask.getId());
    }

    @Override
    public void deleteSubtask(Subtask subtask) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "DELETE FROM subtask WHERE id = ?";
        prepareExecutor().delete(sql, subtask.getId());
    }

    @Override
    public Subtask getSubtask(int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM subtask WHERE id = ?";
        List<Subtask> result = prepareExecutor().query(sql, new SubtaskMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Subtask> getAllSubtaskOfTask(int taskId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM subtask WHERE task_id = ?";
        return prepareExecutor().query(sql, new SubtaskMapper(), taskId);
    }
}

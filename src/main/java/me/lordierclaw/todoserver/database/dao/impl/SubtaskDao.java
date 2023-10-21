package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.ISubtaskDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.SubtaskMapper;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public class SubtaskDao extends AbstractDao implements ISubtaskDao {
    public SubtaskDao(IQueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public Integer insertSubtask(Subtask subtask) {
        String sql = "INSERT INTO subtask (status, name, task_id) " +
                "VALUES (?, ?, ?)";
        return prepareExecutor().insert(sql, subtask.getStatus(), subtask.getName(), subtask.getTaskId());
    }

    @Override
    public Boolean updateSubtask(Subtask subtask) {
        String sql = "UPDATE subtask SET status = ?, name = ? WHERE id = ?";
        return prepareExecutor().update(sql, subtask.getId());
    }

    @Override
    public Boolean deleteSubtask(Subtask subtask) {
        String sql = "DELETE FROM subtask WHERE id = ?";
        return prepareExecutor().delete(sql, subtask.getId());
    }

    @Override
    public Subtask getSubtask(int id) {
        String sql = "SELECT * FROM subtask WHERE id = ?";
        List<Subtask> result = prepareExecutor().query(sql, new SubtaskMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Subtask> getAllSubtaskOfTask(int taskId) {
        String sql = "SELECT * FROM subtask WHERE task_id = ?";
        return prepareExecutor().query(sql, new SubtaskMapper(), taskId);
    }
}

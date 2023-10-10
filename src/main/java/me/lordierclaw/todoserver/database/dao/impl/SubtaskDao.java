package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.ISubtaskDao;
import me.lordierclaw.todoserver.database.mapper.impl.SubtaskMapper;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public class SubtaskDao extends AbstractDao implements ISubtaskDao {
    @Override
    public int insertSubtask(Subtask subtask) {
        String sql = "INSERT INTO subtask (status, name, task_id) " +
                "VALUES (?, ?, ?)";
        return insert(sql, subtask.getStatus(), subtask.getName(), subtask.getTaskId());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        String sql = "UPDATE subtask SET status = ?, name = ? WHERE id = ?";
        update(sql, subtask.getId());
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        String sql = "DELETE FROM subtask WHERE id = ?";
        delete(sql, subtask.getId());
    }

    @Override
    public Subtask getSubtask(int id) {
        String sql = "SELECT * FROM subtask WHERE id = ?";
        List<Subtask> result = query(sql, new SubtaskMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Subtask> getAllSubtaskOfTask(int taskId) {
        String sql = "SELECT * FROM subtask WHERE task_id = ?";
        return query(sql, new SubtaskMapper(), taskId);
    }
}

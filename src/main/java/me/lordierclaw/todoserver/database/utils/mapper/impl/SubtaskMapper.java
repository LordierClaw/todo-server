package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.RowMapper;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskMapper implements RowMapper<Subtask> {
    @Override
    public Subtask mapRow(ResultSet rs) throws SQLMappingException {
        try {
            Subtask subtask = new Subtask();
            subtask.setId(rs.getInt("id"));
            subtask.setName(rs.getString("name"));
            subtask.setStatus(rs.getBoolean("status"));
            subtask.setTaskId(rs.getInt("task_id"));
            return subtask;
        } catch (SQLException e) {
            throw new SQLMappingException(e);
        }
    }
}

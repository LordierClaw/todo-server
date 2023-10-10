package me.lordierclaw.todoserver.database.mapper.impl;

import me.lordierclaw.todoserver.database.mapper.IRowMapper;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubtaskMapper implements IRowMapper<Subtask> {
    @Override
    public Subtask mapRow(ResultSet rs) {
        try {
            Subtask subtask = new Subtask();
            subtask.setId(rs.getInt("id"));
            subtask.setName(rs.getString("name"));
            subtask.setStatus(rs.getBoolean("status"));
            subtask.setTaskId(rs.getInt("task_id"));
            return subtask;
        } catch (SQLException e) {
            return null;
        }
    }
}

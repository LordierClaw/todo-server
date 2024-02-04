package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;
import me.lordierclaw.todoserver.model.base.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements IRowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs) throws SQLMappingException {
        try {
            Task task = new Task();
            task.setId(rs.getInt("id"));
            task.setName(rs.getString("name"));
            task.setStatus(rs.getBoolean("status"));
            task.setUserId(rs.getInt("user_id"));
            task.setCategoryId(rs.getInt("category_id"));
            task.setDueDate(rs.getTimestamp("due_date"));
            task.setReminderAt(rs.getInt("reminder_at"));
            task.setRepeatType(rs.getString("repeat_type"));
            task.setNotes(rs.getString("notes"));
            return task;
        } catch (SQLException e) {
            throw new SQLMappingException(e);
        }
    }
}

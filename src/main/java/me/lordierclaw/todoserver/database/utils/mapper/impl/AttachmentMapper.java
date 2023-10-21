package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttachmentMapper implements IRowMapper<Attachment> {
    @Override
    public Attachment mapRow(ResultSet rs) {
        try {
            Attachment attachment = new Attachment();
            attachment.setId(rs.getInt("id"));
            attachment.setType(rs.getString("type"));
            attachment.setUrl(rs.getString("url"));
            attachment.setTaskId(rs.getInt("task_id"));
            return attachment;
        } catch (SQLException e) {
            return null;
        }
    }
}

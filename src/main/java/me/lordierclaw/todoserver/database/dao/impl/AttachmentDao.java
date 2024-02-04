package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.IAttachmentDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.AttachmentMapper;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public class AttachmentDao extends AbstractDao implements IAttachmentDao {
    public AttachmentDao(IQueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public int insertAttachment(Attachment attachment) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "INSERT INTO attachment (type, url, task_id) " +
                "VALUES (?, ?, ?)";
        return prepareExecutor().insert(sql, attachment.getType(), attachment.getUrl(), attachment.getTaskId());
    }

    @Override
    public void updateAttachment(Attachment attachment) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "UPDATE attachment SET type = ?, url = ? WHERE id = ?";
        prepareExecutor().update(sql, attachment.getId());
    }

    @Override
    public void deleteAttachment(Attachment attachment) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "DELETE FROM attachment WHERE id = ?";
        prepareExecutor().delete(sql, attachment.getId());
    }

    @Override
    public Attachment getAttachment(int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM attachment WHERE id = ?";
        List<Attachment> result = prepareExecutor().query(sql, new AttachmentMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Attachment> getAllAttachmentsOfTask(int taskId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM attachment WHERE task_id = ?";
        return prepareExecutor().query(sql, new AttachmentMapper(), taskId);
    }
}

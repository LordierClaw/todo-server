package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.AttachmentDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.AttachmentMapper;
import me.lordierclaw.todoserver.database.utils.mapper.impl.SingleValueMapper;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutorBuilder;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public class AttachmentDaoImpl extends AbstractDao implements AttachmentDao {
    public AttachmentDaoImpl(QueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public boolean isAttachmentBelongToUser(int userId, int taskId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT COUNT(*) FROM attachment WHERE id = ? AND task_id IN (SELECT id FROM task WHERE id = ? AND user_id = ?)";
        List<Integer> list = prepareExecutor().query(sql, new SingleValueMapper<>(Integer.class), id, taskId, userId);
        return list != null && !list.isEmpty() && list.get(0) != 0;
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

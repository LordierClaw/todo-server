package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.IAttachmentDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.AttachmentMapper;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public class AttachmentDao extends AbstractDao implements IAttachmentDao {
    public AttachmentDao(IQueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public Integer insertAttachment(Attachment attachment) {
        String sql = "INSERT INTO attachment (type, url, task_id) " +
                "VALUES (?, ?, ?)";
        return prepareExecutor().insert(sql, attachment.getType(), attachment.getUrl(), attachment.getTaskId());
    }

    @Override
    public Boolean updateAttachment(Attachment attachment) {
        String sql = "UPDATE attachment SET type = ?, url = ? WHERE id = ?";
        return prepareExecutor().update(sql, attachment.getId());
    }

    @Override
    public Boolean deleteAttachment(Attachment attachment) {
        String sql = "DELETE FROM attachment WHERE id = ?";
        return prepareExecutor().delete(sql, attachment.getId());
    }

    @Override
    public Attachment getAttachment(int id) {
        String sql = "SELECT * FROM attachment WHERE id = ?";
        List<Attachment> result = prepareExecutor().query(sql, new AttachmentMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Attachment> getAllAttachmentsOfTask(int taskId) {
        String sql = "SELECT * FROM attachment WHERE task_id = ?";
        return prepareExecutor().query(sql, new AttachmentMapper(), taskId);
    }
}

package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.IAttachmentDao;
import me.lordierclaw.todoserver.database.mapper.impl.AttachmentMapper;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public class AttachmentDao extends AbstractDao implements IAttachmentDao {
    @Override
    public int insertAttachment(Attachment attachment) {
        String sql = "INSERT INTO attachment (type, url, task_id) " +
                "VALUES (?, ?, ?)";
        return insert(sql, attachment.getType(), attachment.getUrl(), attachment.getTaskId());
    }

    @Override
    public void updateAttachment(Attachment attachment) {
        String sql = "UPDATE attachment SET type = ?, url = ? WHERE id = ?";
        update(sql, attachment.getId());
    }

    @Override
    public void deleteAttachment(Attachment attachment) {
        String sql = "DELETE FROM attachment WHERE id = ?";
        delete(sql, attachment.getId());
    }

    @Override
    public Attachment getAttachment(int id) {
        String sql = "SELECT * FROM attachment WHERE id = ?";
        List<Attachment> result = query(sql, new AttachmentMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Attachment> getAllAttachmentsOfTask(int taskId) {
        String sql = "SELECT * FROM attachment WHERE task_id = ?";
        return query(sql, new AttachmentMapper(), taskId);
    }
}

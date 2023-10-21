package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.model.base.Attachment;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.IAttachmentRepository;

import java.util.List;

public class AttachmentRepository extends AbstractRepository implements IAttachmentRepository {
    @Override
    public int insertAttachment(Attachment attachment) {
        return databaseInstance.getAttachmentDao().insertAttachment(attachment);
    }

    @Override
    public boolean updateAttachment(Attachment attachment) {
        return databaseInstance.getAttachmentDao().updateAttachment(attachment);
    }

    @Override
    public boolean deleteAttachment(Attachment attachment) {
        return databaseInstance.getAttachmentDao().deleteAttachment(attachment);
    }

    @Override
    public Attachment getAttachment(int id) {
        return databaseInstance.getAttachmentDao().getAttachment(id);
    }

    @Override
    public List<Attachment> getAllAttachmentsOfTask(int taskId) {
        return databaseInstance.getAttachmentDao().getAllAttachmentsOfTask(taskId);
    }
}

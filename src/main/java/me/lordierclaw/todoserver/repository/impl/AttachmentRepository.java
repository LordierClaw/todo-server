package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.model.base.Attachment;
import me.lordierclaw.todoserver.repository.IAttachmentRepository;

import javax.inject.Inject;
import java.util.List;

public class AttachmentRepository implements IAttachmentRepository {
    @Inject
    private AbstractDatabaseInstance databaseInstance;

    @Override
    public int insertAttachment(Attachment attachment) {
        return databaseInstance.getAttachmentDao().insertAttachment(attachment);
    }

    @Override
    public void updateAttachment(Attachment attachment) {
        databaseInstance.getAttachmentDao().updateAttachment(attachment);
    }

    @Override
    public void deleteAttachment(Attachment attachment) {
        databaseInstance.getAttachmentDao().deleteAttachment(attachment);
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

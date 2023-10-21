package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public interface IAttachmentDao {
    Integer insertAttachment(Attachment attachment);
    Boolean updateAttachment(Attachment attachment);
    Boolean deleteAttachment(Attachment attachment);
    Attachment getAttachment(int id);
    List<Attachment> getAllAttachmentsOfTask(int taskId);
}

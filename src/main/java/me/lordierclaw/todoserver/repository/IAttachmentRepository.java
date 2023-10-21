package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public interface IAttachmentRepository {
    int insertAttachment(Attachment attachment);
    boolean updateAttachment(Attachment attachment);
    boolean deleteAttachment(Attachment attachment);
    Attachment getAttachment(int id);
    List<Attachment> getAllAttachmentsOfTask(int taskId);
}

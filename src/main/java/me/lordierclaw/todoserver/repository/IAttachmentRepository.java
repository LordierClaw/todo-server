package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public interface IAttachmentRepository {
    int insertAttachment(Attachment attachment) throws DataCrudException, DataInvalidateException;

    void updateAttachment(Attachment attachment) throws DataCrudException, DataInvalidateException;

    void deleteAttachment(Attachment attachment) throws DataCrudException, DataInvalidateException;

    Attachment getAttachment(int id) throws DataCrudException;

    List<Attachment> getAllAttachmentsOfTask(int taskId) throws DataCrudException;
}

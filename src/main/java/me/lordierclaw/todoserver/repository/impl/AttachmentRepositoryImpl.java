package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Attachment;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.AttachmentRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttachmentRepositoryImpl extends AbstractRepository implements AttachmentRepository {
    @Override
    public boolean isAttachmentBelongToUser(int userId, int taskId, int id) throws DataCrudException {
        try {
            return databaseInstance.getAttachmentDao().isAttachmentBelongToUser(userId, taskId, id);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public int insertAttachment(Attachment attachment) throws DataCrudException, DataInvalidateException {
        try {
            int result = databaseInstance.getAttachmentDao().insertAttachment(attachment);
            invalidate();
            return result;
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepositoryImpl.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void updateAttachment(Attachment attachment) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getAttachmentDao().updateAttachment(attachment);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepositoryImpl.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void deleteAttachment(Attachment attachment) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getAttachmentDao().deleteAttachment(attachment);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepositoryImpl.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public Attachment getAttachment(int id) throws DataCrudException {
        try {
            return databaseInstance.getAttachmentDao().getAttachment(id);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Attachment> getAllAttachmentsOfTask(int taskId) throws DataCrudException {
        try {
            return databaseInstance.getAttachmentDao().getAllAttachmentsOfTask(taskId);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }
}

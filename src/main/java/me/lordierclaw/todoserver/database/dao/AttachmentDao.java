package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Attachment;

import java.util.List;

public interface AttachmentDao {
    boolean isAttachmentBelongToUser(int userId, int taskId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    int insertAttachment(Attachment attachment) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    void updateAttachment(Attachment attachment) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    void deleteAttachment(Attachment attachment) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    Attachment getAttachment(int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Attachment> getAllAttachmentsOfTask(int taskId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;
}

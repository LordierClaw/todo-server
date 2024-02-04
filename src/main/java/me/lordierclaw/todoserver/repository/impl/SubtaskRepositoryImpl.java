package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Subtask;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.SubtaskRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubtaskRepositoryImpl extends AbstractRepository implements SubtaskRepository {
    @Override
    public int insertSubtask(Subtask subtask) throws DataCrudException, DataInvalidateException {
        try {
            int result = databaseInstance.getSubtaskDao().insertSubtask(subtask);
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
    public void updateSubtask(Subtask subtask) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getSubtaskDao().updateSubtask(subtask);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepositoryImpl.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void deleteSubtask(Subtask subtask) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getSubtaskDao().deleteSubtask(subtask);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepositoryImpl.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public Subtask getSubtask(int id) throws DataCrudException {
        try {
            return databaseInstance.getSubtaskDao().getSubtask(id);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Subtask> getAllSubtaskOfTask(int taskId) throws DataCrudException {
        try {
            return databaseInstance.getSubtaskDao().getAllSubtaskOfTask(taskId);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }
}

package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.ITaskRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskRepository extends AbstractRepository implements ITaskRepository {
    @Override
    public int insertTask(Task task) throws DataCrudException, DataInvalidateException {
        try {
            int result = databaseInstance.getTaskDao().insertTask(task);
            invalidate();
            return result;
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void updateTask(Task task) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getTaskDao().updateTask(task);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void deleteTask(Task task) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getTaskDao().deleteTask(task);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public Task getTask(int userId, int id) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getTask(userId, id);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Task> getAllTaskOfUser(int userId) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getAllTaskOfUser(userId);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Task> getAllTaskInCategory(int userId, int categoryId) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getAllTaskInCategory(userId, categoryId);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getAllTaskOfUserContainsTitle(userId, keyword);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getAllTaskOfUserInRangeTime(userId, startTime, endTime);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Task> getTaskCountByStatusOfUser(int userId, boolean status) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getTaskCountByStatusOfUser(userId, status);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<CategoryCountDto> getCategoryCountsOfUser(int userId) throws DataCrudException {
        try {
            return databaseInstance.getTaskDao().getCategoryCountsOfUser(userId);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }
}

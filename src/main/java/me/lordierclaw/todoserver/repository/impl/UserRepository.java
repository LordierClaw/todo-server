package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.IUserRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository extends AbstractRepository implements IUserRepository {
    @Override
    public User findUserByEmail(String email) throws DataCrudException {
        try {
            return databaseInstance.getUserDao().findUserByEmail(email);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public int insertUser(User user) throws DataCrudException {
        try {
            return databaseInstance.getUserDao().insertUser(user);
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void updateUser(User user) throws DataCrudException {
        try {
            databaseInstance.getUserDao().updateUser(user);
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void deleteUser(User user) throws DataCrudException {
        try {
            databaseInstance.getUserDao().deleteUser(user);
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }
}

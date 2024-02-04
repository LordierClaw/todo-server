package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.User;

public interface UserDao {
    User findUserByEmail(String email) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    int insertUser(User user) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    void updateUser(User user) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    void deleteUser(User user) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;
}

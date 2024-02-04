package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.UserDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.UserMapper;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutorBuilder;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.User;

import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(QueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public User findUserByEmail(String email) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM user WHERE email = ?";
        List<User> result = prepareExecutor().query(sql, new UserMapper(), email);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public int insertUser(User user) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "INSERT INTO user (email, name, password) " +
                "VALUES (?, ?, ?)";
        return prepareExecutor().insert(sql, "id", user.getEmail(), user.getName(), user.getPassword());
    }

    @Override
    public void updateUser(User user) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "UPDATE user SET email = ?, name = ?, password = ? WHERE id = ?";
        prepareExecutor().update(sql, user.getEmail(), user.getName(), user.getPassword(), user.getId());
    }

    @Override
    public void deleteUser(User user) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "DELETE FROM user WHERE id = ?";
        prepareExecutor().delete(sql, user.getId());
    }
}

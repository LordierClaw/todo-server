package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.IUserDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.UserMapper;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.model.base.User;

import java.util.List;

public class UserDao extends AbstractDao implements IUserDao {
    public UserDao(IQueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public User findUserByEmailPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        List<User> result = prepareExecutor().query(sql, new UserMapper(), email, password);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public Integer insertUser(User user) {
        String sql = "INSERT INTO user (email, name, password) " +
                "VALUES (?, ?, ?)";
        return prepareExecutor().insert(sql, user.getEmail(), user.getName(), user.getPassword());
    }

    @Override
    public Boolean updateUser(User user) {
        String sql = "UPDATE user SET email = ?, name = ?, password = ? WHERE id = ?";
        return prepareExecutor().update(sql, user.getEmail(), user.getName(), user.getPassword(), user.getId());
    }

    @Override
    public Boolean deleteUser(User user) {
        String sql = "DELETE FROM user WHERE id = ?";
        return prepareExecutor().delete(sql, user.getId());
    }
}

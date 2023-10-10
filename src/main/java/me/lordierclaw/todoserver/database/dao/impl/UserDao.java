package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.IUserDao;
import me.lordierclaw.todoserver.database.mapper.impl.UserMapper;
import me.lordierclaw.todoserver.model.base.User;

import java.util.List;

public class UserDao extends AbstractDao implements IUserDao {
    @Override
    public User findUserByEmailPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        List<User> result = query(sql, new UserMapper(), email, password);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public int insertUser(User user) {
        String sql = "INSERT INTO user (email, name, password) " +
                "VALUES (?, ?, ?)";
        return insert(sql, user.getEmail(), user.getName(), user.getPassword());
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE user SET email = ?, name = ?, password = ? WHERE id = ?";
        update(sql, user.getEmail(), user.getName(), user.getPassword(), user.getId());
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM user WHERE id = ?";
        delete(sql, user.getId());
    }
}

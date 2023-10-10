package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.base.User;

public interface IUserDao {
    User findUserByEmailPassword(String email, String password);
    int insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}

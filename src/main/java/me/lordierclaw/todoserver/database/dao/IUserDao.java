package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.base.User;

public interface IUserDao {
    User findUserByEmailPassword(String email, String password);
    Integer insertUser(User user);
    Boolean updateUser(User user);
    Boolean deleteUser(User user);
}

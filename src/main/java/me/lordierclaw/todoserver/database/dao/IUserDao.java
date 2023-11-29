package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.base.User;

public interface IUserDao {
    User findUserByEmail(String email);
    Integer insertUser(User user);
    Boolean updateUser(User user);
    Boolean deleteUser(User user);
}

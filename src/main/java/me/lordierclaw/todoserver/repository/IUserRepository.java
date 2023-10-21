package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.User;

public interface IUserRepository {
    User findUserByEmailPassword(String email, String password);
    int insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(User user);
}

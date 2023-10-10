package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.User;

public interface IUserRepository {
    User findUserByEmailPassword(String email, String password);
    int insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}

package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.model.base.User;

public interface UserRepository {
    User findUserByEmail(String email) throws DataCrudException;

    int insertUser(User user) throws DataCrudException;

    void updateUser(User user) throws DataCrudException;

    void deleteUser(User user) throws DataCrudException;
}

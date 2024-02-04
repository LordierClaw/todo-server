package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.model.base.User;

public interface IUserService {
    String logIn(String email, String password) throws ResponseException;

    int insertUser(User user) throws ResponseException;

    void updateUser(String token, User user) throws ResponseException;

    void deleteUser(String token, User user) throws ResponseException;
}

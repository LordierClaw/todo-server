package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.service.exception.LoginException;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

public interface IUserService {
    String logIn(String email, String password) throws LoginException;
    int insertUser(User user);
    void updateUser(String token, User user) throws UnauthorizedException;
    void deleteUser(String token, User user) throws UnauthorizedException;
}

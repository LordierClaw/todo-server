package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.repository.IUserRepository;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.IUserService;
import me.lordierclaw.todoserver.service.exception.LoginException;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import javax.inject.Inject;

public class UserService extends AuthorizedService implements IUserService {
    @Inject
    private IUserRepository userRepository;

    @Override
    public String logIn(String email, String password) throws LoginException {
        User user =  userRepository.findUserByEmailPassword(email, password);
        if (user == null) {
            throw new LoginException("Email or password doesn't match with system");
        }
        return userManager.setUserSession(user);
    }

    @Override
    public int insertUser(User user) {
        return userRepository.insertUser(user);
    }

    @Override
    public void updateUser(String token, User user) throws UnauthorizedException {
        authorizationMatch(token, user);
        userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(String token, User user) throws UnauthorizedException {
        authorizationMatch(token, user);
        userRepository.deleteUser(user);
    }

}

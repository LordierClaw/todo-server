package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.repository.IUserRepository;
import me.lordierclaw.todoserver.security.IPasswordEncoder;
import me.lordierclaw.todoserver.security.ITokenProvider;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.IUserService;
import me.lordierclaw.todoserver.service.exception.LoginException;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import javax.inject.Inject;

public class UserService extends AuthorizedService implements IUserService {
    @Inject
    private IUserRepository userRepository;
    @Inject
    private IPasswordEncoder passwordEncoder;
    @Inject
    private ITokenProvider tokenProvider;

    @Override
    public String logIn(String email, String password) throws LoginException {
        User user =  userRepository.findUserByEmail(email);
        if (user == null) {
            throw new LoginException("No user with found with email: " + email);
        }
        if (!passwordEncoder.compare(user.getPassword(), password)) {
            throw new LoginException("Incorrect password");
        }
        return tokenProvider.newAccessToken(user.getId());
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

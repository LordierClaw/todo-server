package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.repository.IUserRepository;
import me.lordierclaw.todoserver.security.IPasswordEncoder;
import me.lordierclaw.todoserver.security.ITokenProvider;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.IUserService;

import javax.inject.Inject;

public class UserService extends AuthorizedService implements IUserService {
    @Inject
    private IUserRepository userRepository;
    @Inject
    private IPasswordEncoder passwordEncoder;
    @Inject
    private ITokenProvider tokenProvider;

    @Override
    public String logIn(String email, String password) throws ResponseException {
        User user;
        try {
            user = userRepository.findUserByEmail(email);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
        if (user == null) {
            throw new ResponseException(ResponseValue.USER_NOT_FOUND);
        }
        if (!passwordEncoder.compare(user.getPassword(), password)) {
            throw new ResponseException(ResponseValue.WRONG_PASSWORD);
        }
        return tokenProvider.newAccessToken(user.getId());
    }

    @Override
    public int insertUser(User user) throws ResponseException {
        try {
            return userRepository.insertUser(user);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public void updateUser(String token, User user) throws ResponseException {
        authorizationMatch(token, user);
        try {
            userRepository.updateUser(user);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public void deleteUser(String token, User user) throws ResponseException {
        authorizationMatch(token, user);
        try {
            userRepository.deleteUser(user);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}

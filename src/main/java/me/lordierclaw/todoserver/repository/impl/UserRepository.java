package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.repository.IUserRepository;

import javax.inject.Inject;

public class UserRepository implements IUserRepository {
    @Inject
    private AbstractDatabaseInstance databaseInstance;

    @Override
    public User findUserByEmailPassword(String email, String password) {
        return databaseInstance.getUserDao().findUserByEmailPassword(email, password);
    }

    @Override
    public int insertUser(User user) {
        return databaseInstance.getUserDao().insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        databaseInstance.getUserDao().updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        databaseInstance.getUserDao().deleteUser(user);
    }
}

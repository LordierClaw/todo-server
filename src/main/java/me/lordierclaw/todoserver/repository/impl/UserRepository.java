package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.IUserRepository;

public class UserRepository extends AbstractRepository implements IUserRepository {
    @Override
    public User findUserByEmail(String email) {
        return databaseInstance.getUserDao().findUserByEmail(email);
    }

    @Override
    public int insertUser(User user) {
        return databaseInstance.getUserDao().insertUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return databaseInstance.getUserDao().updateUser(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return databaseInstance.getUserDao().deleteUser(user);
    }
}

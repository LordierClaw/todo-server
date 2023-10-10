package me.lordierclaw.todoserver.database.instance.impl;

import me.lordierclaw.todoserver.database.dao.*;
import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseInstance extends AbstractDatabaseInstance {
    private @Inject ITaskDao taskDao;
    private @Inject ISubtaskDao subtaskDao;
    private @Inject ICategoryDao categoryDao;
    private @Inject IAttachmentDao attachmentDao;
    private @Inject IUserDao userDao;

    DatabaseInstance() {
        initExecutor(4);
    }

    @Override
    public ITaskDao getTaskDao() {
        return taskDao;
    }

    @Override
    public ISubtaskDao getSubtaskDao() {
        return subtaskDao;
    }

    @Override
    public ICategoryDao getCategoryDao() {
        return categoryDao;
    }

    @Override
    public IAttachmentDao getAttachmentDao() {
        return attachmentDao;
    }

    @Override
    public IUserDao getUserDao() {
        return userDao;
    }
}

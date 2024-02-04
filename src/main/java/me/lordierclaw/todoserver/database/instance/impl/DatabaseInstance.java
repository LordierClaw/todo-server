package me.lordierclaw.todoserver.database.instance.impl;

import me.lordierclaw.todoserver.database.connector.IDatabaseConnector;
import me.lordierclaw.todoserver.database.dao.*;
import me.lordierclaw.todoserver.database.dao.impl.*;
import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.database.utils.query.impl.QueryExecutor;
import me.lordierclaw.todoserver.database.utils.trigger.ITriggerTracker;
import me.lordierclaw.todoserver.database.utils.trigger.impl.TriggerTracker;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DatabaseInstance extends AbstractDatabaseInstance {
    private final IDatabaseConnector dbConnector;
    private ITaskDao taskDao;
    private ISubtaskDao subtaskDao;
    private ICategoryDao categoryDao;
    private IAttachmentDao attachmentDao;
    private IUserDao userDao;
    private TriggerTracker triggerTracker;

    @Inject
    DatabaseInstance(IDatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
        initExecutor(4);
        initDaoAndTracker();
    }

    private void initDaoAndTracker() {
        try {
            IQueryExecutorBuilder executorBuilder = () -> QueryExecutor.connect(dbConnector);
            userDao = new UserDao(executorBuilder);
            taskDao = new TaskDao(executorBuilder);
            subtaskDao = new SubtaskDao(executorBuilder);
            categoryDao = new CategoryDao(executorBuilder);
            attachmentDao = new AttachmentDao(executorBuilder);
            String[] trackTables = new String[]{"task", "subtask", "category", "attachment"};
            triggerTracker = new TriggerTracker(executorBuilder, trackTables);
            triggerTracker.stopTracking();
            triggerTracker.createTrackingTable();
            triggerTracker.startTracking();
        } catch (Exception e) {
            Logger.getLogger(DatabaseInstance.class.getName()).log(Level.SEVERE,
                    "Unable to initialize DAO and TriggerTracker", e);
        }
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

    @Override
    public ITriggerTracker getTriggerTracker() {
        return this.triggerTracker;
    }
}

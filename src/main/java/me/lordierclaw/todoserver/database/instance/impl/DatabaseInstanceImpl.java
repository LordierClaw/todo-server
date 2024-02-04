package me.lordierclaw.todoserver.database.instance.impl;

import me.lordierclaw.todoserver.database.connector.DatabaseConnector;
import me.lordierclaw.todoserver.database.dao.*;
import me.lordierclaw.todoserver.database.dao.impl.*;
import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutorBuilder;
import me.lordierclaw.todoserver.database.utils.query.impl.QueryExecutorImpl;
import me.lordierclaw.todoserver.database.utils.trigger.TriggerTracker;
import me.lordierclaw.todoserver.database.utils.trigger.impl.TriggerTrackerImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class DatabaseInstanceImpl extends AbstractDatabaseInstance {
    private final DatabaseConnector dbConnector;
    private TaskDao taskDao;
    private SubtaskDao subtaskDao;
    private CategoryDao categoryDao;
    private AttachmentDao attachmentDao;
    private UserDao userDao;
    private TriggerTracker triggerTracker;

    @Inject
    DatabaseInstanceImpl(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
        initExecutor(4);
        initDaoAndTracker();
    }

    private void initDaoAndTracker() {
        try {
            QueryExecutorBuilder executorBuilder = () -> QueryExecutorImpl.connect(dbConnector);
            userDao = new UserDaoImpl(executorBuilder);
            taskDao = new TaskDaoImpl(executorBuilder);
            subtaskDao = new SubtaskDaoImpl(executorBuilder);
            categoryDao = new CategoryDaoImpl(executorBuilder);
            attachmentDao = new AttachmentDaoImpl(executorBuilder);
            String[] trackTables = new String[]{"task", "subtask", "category", "attachment"};
            triggerTracker = new TriggerTrackerImpl(executorBuilder, trackTables);
            triggerTracker.stopTracking();
            triggerTracker.createTrackingTable();
            triggerTracker.startTracking();
        } catch (Exception e) {
            Logger.getLogger(DatabaseInstanceImpl.class.getName()).log(Level.SEVERE,
                    "Unable to initialize DAO and TriggerTracker", e);
        }
    }

    @Override
    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Override
    public SubtaskDao getSubtaskDao() {
        return subtaskDao;
    }

    @Override
    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    @Override
    public AttachmentDao getAttachmentDao() {
        return attachmentDao;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public TriggerTracker getTriggerTracker() {
        return this.triggerTracker;
    }
}

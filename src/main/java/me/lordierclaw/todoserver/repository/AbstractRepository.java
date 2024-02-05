package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.controller.socket.LiveDataSessionHandler;
import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.sql.SQLConnectException;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;
import me.lordierclaw.todoserver.exception.sql.SQLQueryException;
import me.lordierclaw.todoserver.exception.sql.SQLTypeException;

import javax.inject.Inject;
import java.util.Set;

public abstract class AbstractRepository {

    private final LiveDataSessionHandler liveDataSessionHandler = LiveDataSessionHandler.getInstance();
    @Inject
    protected AbstractDatabaseInstance databaseInstance;

    protected void invalidate() throws DataInvalidateException {
        try {
            Set<String> invalidatedTables = databaseInstance.getTriggerTracker().getInvalidatedTables();
            liveDataSessionHandler.invalidate(invalidatedTables);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataInvalidateException(e);
        }
    }
}

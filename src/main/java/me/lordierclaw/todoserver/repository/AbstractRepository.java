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

public class AbstractRepository {

    @Inject
    protected AbstractDatabaseInstance databaseInstance;

    private final LiveDataSessionHandler liveDataSessionHandler = LiveDataSessionHandler.getInstance();

    protected void invalidate() throws DataInvalidateException {
        try {
            Set<String> invalidatedTables = databaseInstance.getTriggerTracker().getInvalidatedTables();
            liveDataSessionHandler.invalidate(invalidatedTables);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataInvalidateException(e);
        }
    }
}

package me.lordierclaw.todoserver.database.utils.trigger;

import me.lordierclaw.todoserver.exception.sql.SQLConnectException;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;
import me.lordierclaw.todoserver.exception.sql.SQLQueryException;
import me.lordierclaw.todoserver.exception.sql.SQLTypeException;

import java.util.Set;

public interface TriggerTracker {
    void createTrackingTable() throws SQLQueryException, SQLTypeException, SQLConnectException;

    void startTracking() throws SQLQueryException, SQLTypeException, SQLConnectException;

    void stopTracking() throws SQLQueryException, SQLTypeException, SQLConnectException;

    Set<String> getInvalidatedTables() throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;
}

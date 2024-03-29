package me.lordierclaw.todoserver.database.connector;

import me.lordierclaw.todoserver.exception.sql.SQLConnectException;

import java.sql.Connection;

public interface DatabaseConnector {
    Connection newConnection() throws SQLConnectException;
}

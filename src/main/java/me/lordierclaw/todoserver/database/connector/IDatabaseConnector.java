package me.lordierclaw.todoserver.database.connector;

import java.sql.Connection;

public interface IDatabaseConnector {
    Connection newConnection();
}

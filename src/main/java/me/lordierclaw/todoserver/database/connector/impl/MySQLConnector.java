package me.lordierclaw.todoserver.database.connector.impl;

import io.github.cdimascio.dotenv.Dotenv;
import me.lordierclaw.todoserver.database.connector.DatabaseConnector;
import me.lordierclaw.todoserver.exception.sql.SQLConnectException;
import me.lordierclaw.todoserver.utils.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector implements DatabaseConnector {
    private final String url;
    private final String user;
    private final String password;

    public MySQLConnector() {
        Dotenv env = ConfigManager.getInstance().load("my_sql_env");
        this.url = env.get("MYSQL_URL");
        this.user = env.get("MYSQL_ACCOUNT");
        this.password = env.get("MYSQL_PASSWORD");
    }

    @Override
    public Connection newConnection() throws SQLConnectException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLConnectException(e);
        }
    }
}

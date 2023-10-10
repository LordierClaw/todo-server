package me.lordierclaw.todoserver.database.connector.impl;

import io.github.cdimascio.dotenv.Dotenv;
import me.lordierclaw.todoserver.database.connector.IDatabaseConnector;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector implements IDatabaseConnector {
    private final String url;
    private final String user;
    private final String password;

    MySQLConnector() {
        String secretDir = System.getenv("todo.secret");
        Dotenv dotenv = Dotenv.configure().directory(secretDir).filename("my_sql_env").load();
        this.url = dotenv.get("MYSQL_URL");
        this.user = dotenv.get("MYSQL_ACCOUNT");
        this.password = dotenv.get("MYSQL_PASSWORD");
    }

    @Override
    public Connection newConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

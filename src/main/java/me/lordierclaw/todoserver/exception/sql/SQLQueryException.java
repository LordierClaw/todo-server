package me.lordierclaw.todoserver.exception.sql;

public class SQLQueryException extends Exception {
    public SQLQueryException() {
    }

    public SQLQueryException(String message) {
        super(message);
    }

    public SQLQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLQueryException(Throwable cause) {
        super(cause);
    }
}

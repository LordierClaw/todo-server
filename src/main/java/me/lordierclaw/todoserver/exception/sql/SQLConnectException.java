package me.lordierclaw.todoserver.exception.sql;

public class SQLConnectException extends Exception {
    public SQLConnectException() {
    }

    public SQLConnectException(String message) {
        super(message);
    }

    public SQLConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLConnectException(Throwable cause) {
        super(cause);
    }
}

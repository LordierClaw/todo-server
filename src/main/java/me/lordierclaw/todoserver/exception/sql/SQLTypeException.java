package me.lordierclaw.todoserver.exception.sql;

public class SQLTypeException extends Exception {
    public SQLTypeException() {
    }

    public SQLTypeException(String message) {
        super(message);
    }

    public SQLTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLTypeException(Throwable cause) {
        super(cause);
    }
}

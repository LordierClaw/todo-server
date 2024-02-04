package me.lordierclaw.todoserver.exception.sql;

public class SQLMappingException extends Exception {
    public SQLMappingException() {
    }

    public SQLMappingException(String message) {
        super(message);
    }

    public SQLMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLMappingException(Throwable cause) {
        super(cause);
    }
}

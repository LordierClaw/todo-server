package me.lordierclaw.todoserver.exception.sql;

public class SQLRollbackException extends Exception {
    public SQLRollbackException() {
    }

    public SQLRollbackException(String message) {
        super(message);
    }

    public SQLRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLRollbackException(Throwable cause) {
        super(cause);
    }

}

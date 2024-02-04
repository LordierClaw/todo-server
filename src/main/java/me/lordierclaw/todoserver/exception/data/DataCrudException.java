package me.lordierclaw.todoserver.exception.data;

public class DataCrudException extends Exception {
    public DataCrudException() {
    }

    public DataCrudException(String message) {
        super(message);
    }

    public DataCrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCrudException(Throwable cause) {
        super(cause);
    }
}

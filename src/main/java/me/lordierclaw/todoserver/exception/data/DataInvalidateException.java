package me.lordierclaw.todoserver.exception.data;

public class DataInvalidateException extends Exception {
    public DataInvalidateException() {
    }

    public DataInvalidateException(String message) {
        super(message);
    }

    public DataInvalidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInvalidateException(Throwable cause) {
        super(cause);
    }
}

package me.lordierclaw.todoserver.exception.response;

import javax.servlet.ServletException;

public class ResponseException extends ServletException {
    private ResponseValue value;

    public ResponseException(ResponseValue value) {
        super("Error code: " + value.getSpecialCode() + "\n" + value.getMessage());
    }

    public ResponseValue getValue() {
        return value;
    }
}

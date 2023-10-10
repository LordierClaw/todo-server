package me.lordierclaw.todoserver.service.exception;

public class LoginException extends Exception {
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}

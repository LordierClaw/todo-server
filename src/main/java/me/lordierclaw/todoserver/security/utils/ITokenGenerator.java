package me.lordierclaw.todoserver.security.utils;

public interface ITokenGenerator {
    int tokenLength = 24;
    String newToken();
}

package me.lordierclaw.todoserver.security;

public interface PasswordEncoder {
    String encode(String password);

    boolean compare(String encodedPassword, String password);
}

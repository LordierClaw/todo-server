package me.lordierclaw.todoserver.security;

public interface IPasswordEncoder {
    String encode(String password);

    boolean compare(String encodedPassword, String password);
}

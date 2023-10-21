package me.lordierclaw.todoserver.utils;

public class Status {
    private Status() {}
    public static final int OK = 200;
    public static final int ACCEPTED = 202;
    public static final int NOT_ACCEPTED = 406;
    public static final int UNAUTHORIZED = 401;
    public static final int BAD_REQUEST = 400;
}

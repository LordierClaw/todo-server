package me.lordierclaw.todoserver.utils;

public class Helper {
    private Helper() {}

    // You might wonder wtf is this? Well, I don't like to deal with null, so here it is:
    // Yes, I will handle all the "null thing" with just a try catch, I know I'm lazy
    public static <T> T requireNotNull(T object) {
        if (object == null) throw new NullPointerException();
        return object;
    }
}

package me.lordierclaw.todoserver.utils;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;

import java.util.Objects;

public class Helper {

    public static <T> T requireNotNull(T object) {
        return Objects.requireNonNull(object);
    }

    public static <T, Ex extends Throwable> T requireNotNull(T object, Ex nullException) throws Ex {
        if (object == null) {
            throw nullException;
        } else {
            return object;
        }
    }

    public static <T> T requireNotNull(T object, ResponseValue value) throws ResponseException {
        if (object == null) {
            throw new ResponseException(value);
        } else {
            return object;
        }
    }
}

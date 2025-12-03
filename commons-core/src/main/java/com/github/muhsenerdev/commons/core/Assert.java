package com.github.muhsenerdev.commons.core;

public class Assert {

    public static <T> T notNull(T object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
        return object;
    }

    public static String hasText(String text, String message) {
        if (text == null || text.trim().isEmpty())
            throw new IllegalArgumentException(message);
        return text;
    }
}

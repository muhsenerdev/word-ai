package com.github.muhsenerdev.commons.core;

import java.util.Collection;
import java.util.Set;

import com.github.muhsenerdev.wordai.words.domain.SessionWord;

public class DomainUtils {

    public static <T> T notNull(T value, String message) {
        if (value == null)
            throw new InvalidDomainObjectException(message);
        return value;
    }

    public static <T> T notNull(T value, String message, String errorCode) {
        if (value == null)
            throw new InvalidDomainObjectException(message, errorCode);
        return value;
    }

    public static String hasText(String text, String message) {
        if (text == null || text.trim().isEmpty())
            throw new InvalidDomainObjectException(message);
        return text;
    }

    public static String hasText(String text, String message, String errorCode) {
        if (text == null || text.trim().isEmpty())
            throw new InvalidDomainObjectException(message, errorCode);
        return text;
    }

    public static void checkLength(String value, int minLength, int maxLength, String message) {
        Assert.notNull(value, "value cannot be null");
        int length = value.length();
        if (length < minLength || length > maxLength)
            throw new InvalidDomainObjectException(message);
    }

    public static void checkLength(String value, int minLength, int maxLength, String message, String errorCode) {
        Assert.notNull(value, "value cannot be null");
        int length = value.length();
        if (length < minLength || length > maxLength)
            throw new InvalidDomainObjectException(message, errorCode);
    }

    public static <T> void notEmpty(Collection<T> coll, String message, String errorCode) {
        if (coll == null || coll.isEmpty())
            throw new InvalidDomainObjectException(message, errorCode);
    }

    public static InvalidDomainObjectException invalidDomainObjectException(String message, String code) {
        return new InvalidDomainObjectException(message, code);
    }

    public static void isTrue(boolean value, String message, String code) {
        if (!value)
            throw new InvalidDomainObjectException(message, code);

    }

    public static <T> void hasSize(Collection<T> collection, int size, String message, String errorCode) {
        if (collection == null || collection.size() != size)
            throw new InvalidDomainObjectException(message, errorCode);
    }

}

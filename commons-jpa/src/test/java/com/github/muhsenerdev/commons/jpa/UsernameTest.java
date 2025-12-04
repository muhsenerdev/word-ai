package com.github.muhsenerdev.commons.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

class UsernameTest {

    @Test
    void should_create_valid_username() {
        String validValue = "validUser";
        Username username = Username.of(validValue);

        assertNotNull(username);
        assertEquals(validValue, username.getValue());
    }

    @Test
    void should_throw_exception_when_value_is_null() {
        assertThrows(InvalidDomainObjectException.class, () -> Username.of(null));
    }

    @Test
    void should_throw_exception_when_value_is_too_short() {
        String shortValue = "a".repeat(Username.MIN_LENGTH - 1); // Less than MIN_LENGTH characters
        assertThrows(InvalidDomainObjectException.class, () -> Username.of(shortValue));
    }

    @Test
    void should_throw_exception_when_value_is_too_long() {
        String longValue = "a".repeat(Username.MAX_LENGTH + 1); // More than 50 characters
        assertThrows(InvalidDomainObjectException.class, () -> Username.of(longValue));
    }

    @Test
    void should_be_equal_when_values_are_same() {
        Username username1 = Username.of("sameUser");
        Username username2 = Username.of("sameUser");

        assertEquals(username1, username2);
        assertEquals(username1.hashCode(), username2.hashCode());
    }

    @Test
    void should_check_boundary_values() {
        String minLengthValue = "a".repeat(Username.MIN_LENGTH);
        Username minUsername = Username.of(minLengthValue);
        assertEquals(minLengthValue, minUsername.getValue());

        String maxLengthValue = "a".repeat(Username.MAX_LENGTH);
        Username maxUsername = Username.of(maxLengthValue);
        assertEquals(maxLengthValue, maxUsername.getValue());
    }

    @Test
    void should_throw_exception_when_value_is_invalid() {
        String invalidValue = "invalid!user";
        assertThrows(InvalidDomainObjectException.class, () -> Username.of(invalidValue));
    }

    static Stream<String> invalidUsernames() {
        return Stream.of(
                "user!name", // Contains disallowed character '!'
                "user@name", // Contains disallowed character '@'
                "user name", // Contains space
                "_username", // Contains disallowed character '_'
                ".username", // Starts with '.'
                "-username", // Starts with '-'
                "username.", // Ends with '.'
                "username-", // Ends with '-'
                "user..name", // Contains double '.'
                "user--name", // Contains double '-'
                "user.-name", // Contains invalid sequence (starts with '.', ends with '-')
                "user-.name", // Contains invalid sequence (starts with '-', ends with '.')
                "user..", // Ends with double '.'
                "--user" // Starts with double '-'

        );
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void should_throw_exception_when_value_is_invalid(String invalidValue) {
        assertThrows(InvalidDomainObjectException.class, () -> Username.of(invalidValue));
    }

    static Stream<String> validUsernames() {
        return Stream.of(
                "username",
                "user123",
                "user_name",
                "user-name",
                "user.name",
                "a".repeat(Username.MIN_LENGTH),
                "a".repeat(Username.MAX_LENGTH),
                "user.123",
                "user-123",
                "user_123",
                "user.name.123",
                "user-name-123",
                "user_name_123",
                "john.doe",
                "jane_smith",
                "admin",
                "guest",
                "testuser");
    }

    @ParameterizedTest
    @MethodSource("validUsernames")
    void should_create_username_with_valid_values(String validValue) {
        Username username = Username.of(validValue);
        assertNotNull(username);
        assertEquals(validValue, username.getValue());
    }

}

package com.github.muhsenerdev.commons.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
}

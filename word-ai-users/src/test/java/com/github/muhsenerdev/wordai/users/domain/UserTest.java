package com.github.muhsenerdev.wordai.users.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

class UserTest {

    @Test
    void builder_shouldCreateUser_whenValidArguments() {
        Username username = TestData.username();
        HashedPassword password = TestData.hashedPassword();

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    void builder_shouldThrowException_whenUsernameIsNull() {
        HashedPassword password = TestData.hashedPassword();

        assertThrows(InvalidDomainObjectException.class, () -> User.builder()
                .username(null)
                .password(password)
                .build());
    }

    @Test
    void builder_shouldThrowException_whenPasswordIsNull() {
        Username username = TestData.username();

        assertThrows(InvalidDomainObjectException.class, () -> User.builder()
                .username(username)
                .password(null)
                .build());
    }
}

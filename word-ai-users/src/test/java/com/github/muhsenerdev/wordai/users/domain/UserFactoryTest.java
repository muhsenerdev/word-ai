package com.github.muhsenerdev.wordai.users.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    private PasswordFactory passwordFactory;

    @InjectMocks
    private UserFactory userFactory;

    private User templateUser;
    private Username username;
    private HashedPassword hashedPassword;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        templateUser = UserTestBuilder.aUser().build();
        username = templateUser.getUsername();
        hashedPassword = templateUser.getPassword();
    }

    @Test
    void create_shouldCreateUser_whenUsingHashedPassword() {
        // When
        User result = userFactory.create(username, hashedPassword);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(hashedPassword, result.getPassword());
    }

    @Test
    void create_shouldCreateUser_whenUsingRawPassword() throws PasswordHashingException {
        // Given
        RawPassword rawPassword = TestData.rawPassword();

        when(passwordFactory.hash(rawPassword)).thenReturn(hashedPassword);

        // When
        User result = userFactory.create(username, rawPassword);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(hashedPassword, result.getPassword());

        verify(passwordFactory).hash(rawPassword);
    }

    @Test
    void create_shouldThrowException_whenHashingFails() throws PasswordHashingException {
        // Given
        RawPassword rawPassword = TestData.rawPassword();

        when(passwordFactory.hash(rawPassword)).thenThrow(new PasswordHashingException("Hashing failed"));

        // When & Then
        assertThrows(PasswordHashingException.class, () -> userFactory.create(username, rawPassword));
    }
}

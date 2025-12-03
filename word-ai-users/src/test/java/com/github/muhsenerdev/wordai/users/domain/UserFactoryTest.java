package com.github.muhsenerdev.wordai.users.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.support.data.TestData;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    private PasswordFactory passwordFactory;

    @InjectMocks
    private UserFactory userFactory;

    private User templateUser;
    private Username username;
    private HashedPassword hashedPassword;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        templateUser = UserTestBuilder.aUser().build();
        username = templateUser.getUsername();
        hashedPassword = templateUser.getPassword();
        roles = Collections.singleton(RoleTestBuilder.aRole().build());
    }

    @Test
    void create_shouldCreateUser_whenUsingHashedPassword() {
        // When
        User result = userFactory.create(username, hashedPassword, roles);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(hashedPassword, result.getPassword());
        assertEquals(roles, result.getRoles());
    }

    @Test
    void create_shouldCreateUser_whenUsingRawPassword() throws PasswordHashingException {
        // Given
        RawPassword rawPassword = TestData.rawPassword();

        when(passwordFactory.hash(rawPassword)).thenReturn(hashedPassword);

        // When
        User result = userFactory.create(username, rawPassword, roles);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(hashedPassword, result.getPassword());
        assertEquals(roles, result.getRoles());
        verify(passwordFactory).hash(rawPassword);
    }

    @Test
    void create_shouldThrowException_whenHashingFails() throws PasswordHashingException {
        // Given
        RawPassword rawPassword = TestData.rawPassword();

        when(passwordFactory.hash(rawPassword)).thenThrow(new PasswordHashingException("Hashing failed"));

        // When & Then
        assertThrows(PasswordHashingException.class, () -> userFactory.create(username, rawPassword, roles));
    }
}

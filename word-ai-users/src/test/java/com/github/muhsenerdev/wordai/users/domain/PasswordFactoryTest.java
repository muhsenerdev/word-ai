package com.github.muhsenerdev.wordai.users.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordFactoryTest {

    @Mock
    private PasswordValidationPolicy validationPolicy;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private PasswordFactory passwordFactory;

    @Test
    @DisplayName("createRawPassword should return a RawPassword when value is not null")
    void createRawPassword_shouldReturnRawPassword_whenValueIsNotNull() throws PasswordValidationException {
        String passwordValue = "validPassword";

        RawPassword result = passwordFactory.createRawPassword(passwordValue);

        assertNotNull(result);
        assertEquals(passwordValue, result.getValue());
        verify(validationPolicy).validateOrThrow(passwordValue);
    }

    @Test
    @DisplayName("createRawPassword should throw IllegalArgumentException when value is null")
    void createRawPassword_shouldThrowIllegalArgumentException_whenValueIsNull() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> passwordFactory.createRawPassword(null));
    }

    @Test
    @DisplayName("createRawPassword should throw PasswordValidationException when validation fails")
    void createRawPassword_shouldThrowException_whenValidationFails() throws PasswordValidationException {
        String passwordValue = "invalid";
        doThrow(new PasswordValidationException("Invalid password")).when(validationPolicy)
                .validateOrThrow(passwordValue);

        assertThrows(PasswordValidationException.class, () -> passwordFactory.createRawPassword(passwordValue));
    }

    @Test
    @DisplayName("hash should return a HashedPassword when RawPassword is not null")
    void hash_shouldReturnHashedPassword_whenRawPasswordIsNotNull() throws PasswordHashingException {
        String rawValue = "password";
        String hashedValue = "hashedPassword";
        RawPassword rawPassword = RawPassword.of(rawValue);

        when(passwordHasher.hash(rawValue)).thenReturn(hashedValue);

        HashedPassword result = passwordFactory.hash(rawPassword);

        assertNotNull(result);
        assertEquals(hashedValue, result.getValue());
        verify(passwordHasher).hash(rawValue);
    }

    @Test
    @DisplayName("hash should return null when RawPassword is null")
    void hash_shouldReturnNull_whenRawPasswordIsNull() throws PasswordHashingException {
        HashedPassword result = passwordFactory.hash(null);

        assertNull(result);
        verifyNoInteractions(passwordHasher);
    }

    @Test
    @DisplayName("hash should throw PasswordHashingException when hashing fails")
    void hash_shouldThrowException_whenHashingFails() throws PasswordHashingException {
        String rawValue = "password";
        RawPassword rawPassword = RawPassword.of(rawValue);
        doThrow(new PasswordHashingException("Hashing failed")).when(passwordHasher).hash(rawValue);

        assertThrows(PasswordHashingException.class, () -> passwordFactory.hash(rawPassword));
    }
}

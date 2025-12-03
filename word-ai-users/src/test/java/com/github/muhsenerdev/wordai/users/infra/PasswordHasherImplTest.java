package com.github.muhsenerdev.wordai.users.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.muhsenerdev.wordai.users.domain.PasswordHashingException;

@ExtendWith(MockitoExtension.class)
class PasswordHasherImplTest {

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private PasswordHasherImpl hasher;

    @Test
    void should_hash_password_successfully() {
        // Given
        String password = "mySecretPassword";
        String expectedHash = "hashedValue";
        when(encoder.encode(password)).thenReturn(expectedHash);

        // When
        String actualHash = hasher.hash(password);

        // Then
        assertEquals(expectedHash, actualHash);
    }

    @Test
    void should_throw_exception_when_password_is_null() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> hasher.hash(null));
    }

    @Test
    void should_wrap_encoder_exception_in_password_hashing_exception() {
        // Given
        String password = "password";
        when(encoder.encode(anyString())).thenThrow(new RuntimeException("Encoding failed"));

        // When/Then
        assertThrows(PasswordHashingException.class, () -> hasher.hash(password));
    }
}

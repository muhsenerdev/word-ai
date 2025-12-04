package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.core.Assert;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordFactory {

    private final PasswordValidationPolicy validationPolicy;
    private final PasswordHasher passwordHasher;

    public RawPassword createRawPassword(String value) throws PasswordValidationException {
        Assert.notNull(value, "Password value cannot be null.");
        validationPolicy.validateOrThrow(value);

        return RawPassword.of(value);
    }

    public @Nullable HashedPassword hash(@Nullable RawPassword rawPassword) throws PasswordHashingException {
        if (rawPassword == null)
            return null;
        String hashedString = passwordHasher.hash(rawPassword.getValue());

        return HashedPassword.of(hashedString);

    }

    public RawPassword createRawPasswordWithNoValidation(String value) throws IllegalArgumentException {
        Assert.hasText(value, "Password value cannot be null or empty.");
        return RawPassword.of(value);
    }

}

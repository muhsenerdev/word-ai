package com.github.muhsenerdev.wordai.users.domain;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordFactory {

    private final PasswordValidationPolicy validationPolicy;
    private final PasswordHasher passwordHasher;

    public @Nullable RawPassword createRawPassword(@Nullable String value) throws PasswordValidationException {
        if (value == null)
            return null;

        validationPolicy.validateOrThrow(value);

        return RawPassword.of(value);
    }

    public @Nullable HashedPassword hash(@Nullable RawPassword rawPassword) throws PasswordHashingException {
        if (rawPassword == null)
            return null;
        String hashedString = passwordHasher.hash(rawPassword.getValue());

        return HashedPassword.of(hashedString);

    }

}

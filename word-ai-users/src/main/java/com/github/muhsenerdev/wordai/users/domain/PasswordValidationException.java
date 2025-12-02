package com.github.muhsenerdev.wordai.users.domain;

public abstract class PasswordValidationException extends PasswordException {

    public PasswordValidationException(String message) {
        super(message);
    }

}

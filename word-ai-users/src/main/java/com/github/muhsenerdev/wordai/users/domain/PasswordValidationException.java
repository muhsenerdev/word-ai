package com.github.muhsenerdev.wordai.users.domain;

public class PasswordValidationException extends PasswordException {

    public PasswordValidationException(String message) {
        super(message);
    }

    public PasswordValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}

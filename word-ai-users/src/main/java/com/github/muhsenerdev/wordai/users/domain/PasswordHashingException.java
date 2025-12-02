package com.github.muhsenerdev.wordai.users.domain;

public class PasswordHashingException extends PasswordException {

    public PasswordHashingException(String message) {
        super(message);
    }

    public PasswordHashingException(String message, Throwable cause) {
        super(message, cause);
    }

}

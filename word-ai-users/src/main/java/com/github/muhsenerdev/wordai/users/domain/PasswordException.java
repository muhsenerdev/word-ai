package com.github.muhsenerdev.wordai.users.domain;

public class PasswordException extends RuntimeException {

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }

}

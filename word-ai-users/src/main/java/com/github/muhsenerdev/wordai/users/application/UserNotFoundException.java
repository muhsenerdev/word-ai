package com.github.muhsenerdev.wordai.users.application;

import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(Username username) {
        super("user", "username", username.getValue(), "user.not-found.username");
    }

    public UserNotFoundException(UserId id) {
        super("user", "id", id.getValue(), "user.not-found.id");
    }
}

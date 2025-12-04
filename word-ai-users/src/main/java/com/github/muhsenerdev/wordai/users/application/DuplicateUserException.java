package com.github.muhsenerdev.wordai.users.application;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.exception.DuplicateResourceException;
import com.github.muhsenerdev.commons.jpa.Username;

public class DuplicateUserException extends DuplicateResourceException {

    public DuplicateUserException(Username username) {
        super("user", "username",
                Assert.notNull(username, "Username cannot be null to construct DuplicateUserException").getValue(),
                "user.username.duplicated");
    }
}

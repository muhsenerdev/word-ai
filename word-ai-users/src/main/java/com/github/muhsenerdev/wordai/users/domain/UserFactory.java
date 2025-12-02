
package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.jpa.Username;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFactory {

    private final PasswordFactory passwordFactory;

    public User create(Username username, HashedPassword password) {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }

    public User create(Username username, RawPassword password) throws PasswordHashingException {
        return User.builder()
                .username(username)
                .password(passwordFactory.hash(password))
                .build();
    }

}

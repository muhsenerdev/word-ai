
package com.github.muhsenerdev.wordai.users.domain;

import java.util.Collection;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.Username;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFactory {

    private final PasswordFactory passwordFactory;

    public User create(Username username, HashedPassword password, Collection<Role> roles)
            throws InvalidDomainObjectException {
        return User.builder()
                .username(username)
                .password(password)
                .roles(roles)
                .build();
    }

    public User create(Username username, RawPassword password, Collection<Role> roles)
            throws PasswordHashingException, InvalidDomainObjectException {
        HashedPassword hashedPassword = passwordFactory.hash(password);
        return create(username, hashedPassword, roles);
    }

}

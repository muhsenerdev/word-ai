
package com.github.muhsenerdev.wordai.users.infra;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.wordai.users.domain.PasswordHasher;
import com.github.muhsenerdev.wordai.users.domain.PasswordHashingException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordHasherImpl implements PasswordHasher {

    private final PasswordEncoder encoder;

    @Override
    public String hash(String password) throws PasswordHashingException {
        Assert.notNull(password, "Password cannot be null");
        try {
            return encoder.encode(password);
        } catch (Exception e) {
            throw new PasswordHashingException(e.getMessage(), e);
        }
    }

}

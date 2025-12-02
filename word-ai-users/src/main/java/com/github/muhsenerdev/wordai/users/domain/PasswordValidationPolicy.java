
package com.github.muhsenerdev.wordai.users.domain;

import java.util.List;

public interface PasswordValidationPolicy {

    public List<String> validate(String rawPassword);

    public void validateOrThrow(String rawPassword) throws PasswordValidationException;

    public boolean isValid(String rawPassword);

}

package com.github.muhsenerdev.wordai.users.infra;

import java.util.List;

import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.stereotype.Component;

import com.github.muhsenerdev.wordai.users.domain.PasswordValidationException;
import com.github.muhsenerdev.wordai.users.domain.PasswordValidationPolicy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PassayPasswordValidator implements PasswordValidationPolicy {

    private final PasswordValidator passwordValidator;

    @Override
    public boolean isValid(String rawPassword) {
        try {
            RuleResult result = passwordValidator.validate(new PasswordData(rawPassword));
            return result.isValid();
        } catch (Exception e) {
            throw new PasswordValidationException(e.getMessage(), e);
        }

    }

    @Override
    public List<String> validate(String rawPassword) {
        try {
            RuleResult result = passwordValidator.validate(new PasswordData(rawPassword));
            return passwordValidator.getMessages(result);
        } catch (Exception e) {
            throw new PasswordValidationException(e.getMessage(), e);
        }

    }

    @Override
    public void validateOrThrow(String rawPassword) throws PasswordValidationException {
        List<String> messages = validate(rawPassword);
        if (messages != null && !messages.isEmpty()) {
            throw new PasswordValidationException(messages.toString());
        }

    }

}

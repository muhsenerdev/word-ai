package com.github.muhsenerdev.wordai.users.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@ConfigurationProperties(prefix = "validation.password")
@Data
@Component
public class PasswordProps {

    private int minLength = 1;
    private int maxLength = 55;
    private int minUppercase;
    private int minLowercase;
    private int minDigit;
    private int minSpecial;
    private boolean allowWhitespace = false;

    @PostConstruct
    public void init() {
        if (minLength >= maxLength) {
            throw new IllegalStateException("minLength must be less than maxLength for password props");
        }

    }
}

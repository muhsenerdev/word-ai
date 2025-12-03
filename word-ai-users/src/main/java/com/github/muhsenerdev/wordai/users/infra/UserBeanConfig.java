package com.github.muhsenerdev.wordai.users.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.muhsenerdev.wordai.users.domain.PasswordFactory;
import com.github.muhsenerdev.wordai.users.domain.PasswordHasher;
import com.github.muhsenerdev.wordai.users.domain.PasswordValidationPolicy;
import com.github.muhsenerdev.wordai.users.domain.UserFactory;

@Configuration
public class UserBeanConfig {

    @Bean
    public UserFactory userFactory(PasswordFactory passwordFactory) {
        return new UserFactory(passwordFactory);
    }

    @Bean
    public PasswordFactory passwordFactory(PasswordValidationPolicy validationPolicy, PasswordHasher hasher) {
        return new PasswordFactory(validationPolicy, hasher);
    }

}

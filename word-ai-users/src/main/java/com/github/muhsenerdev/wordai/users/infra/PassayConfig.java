package com.github.muhsenerdev.wordai.users.infra;

import org.passay.PasswordValidator;
import org.passay.Rule;
import org.springframework.context.annotation.Bean;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.WhitespaceRule;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PassayConfig {

    private final PasswordProps passwordProps;

    @Bean
    public PasswordValidator passwordValidator() {

        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(passwordProps.getMinLength(), passwordProps.getMaxLength()));

        if (passwordProps.getMinUppercase() > 0) {
            rules.add(new CharacterRule(EnglishCharacterData.UpperCase, passwordProps.getMinUppercase()));
        }

        if (passwordProps.getMinLowercase() > 0) {
            rules.add(new CharacterRule(EnglishCharacterData.LowerCase, passwordProps.getMinLowercase()));
        }

        if (passwordProps.getMinDigit() > 0) {
            rules.add(new CharacterRule(EnglishCharacterData.Digit, passwordProps.getMinDigit()));
        }

        if (passwordProps.getMinSpecial() > 0) {
            rules.add(new CharacterRule(EnglishCharacterData.Special, passwordProps.getMinSpecial()));
        }

        if (!passwordProps.isAllowWhitespace()) {
            rules.add(new WhitespaceRule());
        }

        return new PasswordValidator(rules);
    }

}

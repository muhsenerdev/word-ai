package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest;

import com.github.muhsenerdev.wordai.words.domain.Language;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SupportedLanguageValidator implements ConstraintValidator<SupportedLanguage, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            Language.fromString(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

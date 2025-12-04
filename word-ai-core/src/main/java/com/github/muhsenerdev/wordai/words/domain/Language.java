
package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

public enum Language {
    ENGLISH, TURKISH;

    public static Language fromString(String language) {
        Assert.notNull(language, "Language cannot be null");
        try {
            return valueOf(language.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidDomainObjectException("Invalid language: " + language, "language.unknown");
        }
    }
}

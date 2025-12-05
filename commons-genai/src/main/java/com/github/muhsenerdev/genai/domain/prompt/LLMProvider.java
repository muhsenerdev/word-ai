package com.github.muhsenerdev.genai.domain.prompt;

import com.github.muhsenerdev.commons.core.*;;

/**
 * Represents different LLM (Large Language Model) providers.
 */
public enum LLMProvider {
    OPEN_AI, GEMINI;

    /**
     * Converts a string value to the corresponding LLMProvider enum.
     *
     * @param value the string value to convert (case-insensitive)
     * @return the corresponding LLMProvider
     * @throws InvalidDomainObjectException if the value does not match any enum
     *                                      constant
     */
    public static LLMProvider of(String value) throws InvalidDomainObjectException {
        Assert.notNull(value, "LLMProvider value must not be null");

        String normalizedValue = value.trim().toUpperCase();

        try {
            return valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            throw new InvalidDomainObjectException("Unknown LLMProvider: " + value, "llm-provider.unknown");
        }
    }

    /**
     * Converts a string value to the corresponding LLMProvider enum.
     *
     * @param value the string value to convert (case-insensitive)
     * @return the corresponding LLMProvider
     * @throws InvalidDomainObjectException if the value does not match any enum
     *                                      constant
     */
    public static LLMProvider fromString(String value) throws InvalidDomainObjectException {
        return of(value);
    }
}

package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

/**
 * Represents the type of AI response that can be generated.
 */
public enum AiResponseType {
    IMAGE_URL, IMAGE, TEXT;

    /**
     * Converts a string value to the corresponding AiResponseType enum.
     *
     * @param value the string value to convert (case-insensitive, trims whitespace)
     * @return the corresponding AiResponseType
     * @throws InvalidDomainObjectException if the value does not match any enum
     *                                      constant
     */
    public static AiResponseType of(String value) throws InvalidDomainObjectException {
        Assert.notNull(value, "null AiReponseType");
        String normalizedValue = value.trim().toUpperCase();

        try {
            return valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            throw new InvalidDomainObjectException("Unknown AiResponseType: " + value, "ai-response-type.unknown");
        }
    }
}

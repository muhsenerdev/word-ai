package com.github.muhsenerdev.genai.domain.prompt;

import com.github.muhsenerdev.commons.core.DomainException;

public class PayloadValidationException extends DomainException {

    public PayloadValidationException(String message) {
        super(message);
    }

    public PayloadValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.muhsenerdev.commons.core.exception;

public class BusinessValidationException extends ApplicationException {

    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(String message, String code) {
        super(message, code);
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessValidationException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }
}

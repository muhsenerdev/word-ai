package com.github.muhsenerdev.commons.core.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final String code;

    protected ApplicationException(String message) {
        this(message, null, null);
    }

    protected ApplicationException(String message, String code) {
        this(message, code, null);
    }

    protected ApplicationException(String message, Throwable cause) {
        this(message, null, cause);
    }

    protected ApplicationException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}

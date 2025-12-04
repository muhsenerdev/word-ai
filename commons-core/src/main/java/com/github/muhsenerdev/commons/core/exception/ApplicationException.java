package com.github.muhsenerdev.commons.core.exception;

import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class ApplicationException extends RuntimeException {

    @NonNull
    private final String code;

    protected ApplicationException(String message) {
        this(message, "unknown", null);
    }

    protected ApplicationException(String message, String code) {
        this(message, Optional.ofNullable(code).orElse("unknown"), null);
    }

    protected ApplicationException(String message, Throwable cause) {
        this(message, "unknown", cause);
    }

    protected ApplicationException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = Optional.ofNullable(code).orElse("unknown");
    }
}

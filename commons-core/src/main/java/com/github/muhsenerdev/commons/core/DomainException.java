package com.github.muhsenerdev.commons.core;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {

    private final String errorCode;

    public DomainException(String message) {
        super(message);
        this.errorCode = null;
    }

    public DomainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DomainException(String message, Throwable cause) {
        this(message, "unknown", cause);
    }

}

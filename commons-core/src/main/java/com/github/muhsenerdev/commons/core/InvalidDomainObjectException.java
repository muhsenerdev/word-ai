package com.github.muhsenerdev.commons.core;

import lombok.Getter;

@Getter
public class InvalidDomainObjectException extends DomainException {

    private final String errorCode;

    public InvalidDomainObjectException(String message) {
        this(message, null, null);
    }

    public InvalidDomainObjectException(String message, String errorCode) {
        this(message, errorCode, null);
    }

    public InvalidDomainObjectException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
        this.errorCode = errorCode;
    }

}

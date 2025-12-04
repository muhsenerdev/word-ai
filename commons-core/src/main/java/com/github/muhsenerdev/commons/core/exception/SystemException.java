package com.github.muhsenerdev.commons.core.exception;

public class SystemException extends ApplicationException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, String code) {
        super(message, code);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }
}

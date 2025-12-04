package com.github.muhsenerdev.commons.core.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends ApplicationException {

    private final String resource;
    private final String field;
    private final Object value;

    public DuplicateResourceException(String resource, String field, Object value) {
        super(String.format("%s with %s: '%s' already exists", resource, field, value), "duplicate.resource");
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

    public DuplicateResourceException(String resource, String field, Object value, String code) {
        super(String.format("%s with %s: '%s' already exists", resource, field, value), code);
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}

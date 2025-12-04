package com.github.muhsenerdev.commons.core.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ApplicationException {

    private final String resource;
    private final String field;
    private final Object value;

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s with %s: '%s' not found", resource, field, value), "resource.not.found");
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

    public ResourceNotFoundException(String resource, String field, Object value, String code) {
        super(String.format("%s with %s: '%s' not found", resource, field, value), code);
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}

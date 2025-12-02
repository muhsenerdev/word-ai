package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.SingleValueObject;

public class RawPassword extends SingleValueObject<String> {

    private final String value;

    private RawPassword(String value) {
        DomainUtils.notNull(value, "Value cannot be null");
        this.value = value;
    }

    protected static RawPassword of(String value) {
        return new RawPassword(value);
    }

    @Override
    public String getValue() {
        return value;
    }

}

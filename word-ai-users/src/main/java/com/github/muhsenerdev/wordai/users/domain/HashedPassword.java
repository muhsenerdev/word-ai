package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.SingleValueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashedPassword extends SingleValueObject<String> {

    @Column(name = "password")
    private String value;

    private HashedPassword(String value) {
        DomainUtils.hasText(value, "Password value cannot be null or empty");

        this.value = value;
    }

    protected static HashedPassword of(String value) {
        return new HashedPassword(value);
    }

    @Override
    public String getValue() {
        return value;
    }

}

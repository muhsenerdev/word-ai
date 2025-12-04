package com.github.muhsenerdev.commons.jpa;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.SingleValueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Username extends SingleValueObject<String> {

    public static final String REGEX = "^[a-zA-Z0-9](?!.*[._-]{2})[a-zA-Z0-9._-]{1,48}[a-zA-Z0-9]$";

    public static final int MAX_LENGTH = 50;
    public static final int MIN_LENGTH = 3;

    @Column(name = "username")
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

    private Username(String value) {
        DomainUtils.notNull(value, "Username value cannot be null");
        DomainUtils.checkLength(value, MIN_LENGTH, MAX_LENGTH,
                String.format("Username must be between %d and %d.", MAX_LENGTH, MIN_LENGTH, "username.length"));

        if (!value.matches(REGEX))
            throw new InvalidDomainObjectException("Username is not valid.", "username.format");

        this.value = value;
    }

    /**
     * Creates a new {@code Username} instance with the specified value.
     *
     * @param value The username value to be used.
     * @return A new {@code Username} instance with the specified value.
     * @throws InvalidDomainObjectException if the value is null or not between 3
     *                                      and 50 characters long.
     */
    public static Username of(String value) throws InvalidDomainObjectException {
        return new Username(value);
    }

}

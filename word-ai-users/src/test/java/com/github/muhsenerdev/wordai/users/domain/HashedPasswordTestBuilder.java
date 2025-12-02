package com.github.muhsenerdev.wordai.users.domain;

public class HashedPasswordTestBuilder {

    private String value;

    public HashedPasswordTestBuilder() {
        this.value = "{brcypt}test-hashed-password";
    }

    public static HashedPasswordTestBuilder aHashedPassword() {
        return new HashedPasswordTestBuilder();
    }

    public HashedPasswordTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public HashedPassword build() {
        return HashedPassword.of(value);
    }

}

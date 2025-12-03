package com.github.muhsenerdev.wordai.users.domain;

public class PasswordTestBuilder {

    private String value;

    private PasswordTestBuilder() {
        this.value = "TestPassword!1.23";
    }

    public static PasswordTestBuilder aPassword() {
        return new PasswordTestBuilder();
    }

    public PasswordTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public RawPassword build() {
        return RawPassword.of(value);
    }

}

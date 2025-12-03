package com.github.muhsenerdev.wordai.users.support.data;

import java.util.UUID;

import com.github.javafaker.Faker;
import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.HashedPassword;
import com.github.muhsenerdev.wordai.users.domain.HashedPasswordTestBuilder;
import com.github.muhsenerdev.wordai.users.domain.PasswordTestBuilder;
import com.github.muhsenerdev.wordai.users.domain.RawPassword;

public class TestData {

    private static final Faker FAKER = new Faker();

    public static Username username() {
        return Username.of(FAKER.name().username());
    }

    public static RoleName randomRoleName() {
        return RoleName.of(FAKER.name().firstName().replace(" ", "_"));
    }

    public static Username username(String from) {
        return Username.of(from);
    }

    public static HashedPassword hashedPassword() {
        return HashedPasswordTestBuilder.aHashedPassword().build();
    }

    public static RawPassword rawPassword() {
        return PasswordTestBuilder.aPassword().build();
    }

}

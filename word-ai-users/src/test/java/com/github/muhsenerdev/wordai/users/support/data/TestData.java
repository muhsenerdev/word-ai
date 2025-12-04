package com.github.muhsenerdev.wordai.users.support.data;

import java.util.UUID;

import com.github.javafaker.Faker;
import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.application.CreateUserCommand;
import com.github.muhsenerdev.wordai.users.application.UserCreationResponse;
import com.github.muhsenerdev.wordai.users.domain.HashedPassword;
import com.github.muhsenerdev.wordai.users.domain.HashedPasswordTestBuilder;
import com.github.muhsenerdev.wordai.users.domain.PasswordTestBuilder;
import com.github.muhsenerdev.wordai.users.domain.RawPassword;

public class TestData {

    private static final Faker FAKER = new Faker();

    public static Username username() {
        return Username.of("test-usernane" + UUID.randomUUID().toString());
    }

    public static RoleName randomRoleName() {
        return RoleName.of("role:" + UUID.randomUUID().toString());
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

    public static CreateUserCommand createUserCommand() {
        return CreateUserCommand.builder()
                .username(username().getValue())
                .password(rawPassword().getValue())
                .build();
    }

    public static UserCreationResponse userCreationResponse() {
        return UserCreationResponse.builder()
                .id(UUID.randomUUID())
                .username(username().getValue())
                .roles(java.util.Set.of(randomRoleName().getValue()))
                .build();
    }

}

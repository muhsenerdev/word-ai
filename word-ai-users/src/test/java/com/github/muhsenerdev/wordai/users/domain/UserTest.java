package com.github.muhsenerdev.wordai.users.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

class UserTest {

    @Test
    @DisplayName("should create user when valid arguments")
    void builder_shouldCreateUser_whenValidArguments() {
        Username username = TestData.username();
        HashedPassword password = TestData.hashedPassword();

        List<Role> roleList = Collections.singletonList(RoleTestBuilder.aRole().build());
        User user = User.builder()
                .username(username)
                .password(password)
                .roles(roleList)
                .build();

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(roleList.getFirst(), user.getRoles().stream().findFirst().get());
    }

    @Test
    @DisplayName("should throw exception when username is null")
    void builder_shouldThrowException_whenUsernameIsNull() {
        HashedPassword password = TestData.hashedPassword();

        assertThrows(InvalidDomainObjectException.class, () -> User.builder()
                .username(null)
                .password(password)
                .build());
    }

    @Test
    @DisplayName("should throw exception when password is null")
    void builder_shouldThrowException_whenPasswordIsNull() {
        Username username = TestData.username();

        assertThrows(InvalidDomainObjectException.class, () -> User.builder()
                .username(username)
                .password(null)
                .build());
    }

    @Test
    @DisplayName("should throw exception when roles is null")
    void builder_shouldThrowException_whenRolesIsNull() {
        Username username = TestData.username();
        HashedPassword password = TestData.hashedPassword();

        assertThrows(InvalidDomainObjectException.class, () -> User.builder()
                .username(username)
                .password(password)
                .roles(null)
                .build());
    }

    @Test
    @DisplayName("should throw exception when roles is empty")
    void builder_shouldThrowException_whenRolesIsEmpty() {
        Username username = TestData.username();
        HashedPassword password = TestData.hashedPassword();

        assertThrows(InvalidDomainObjectException.class, () -> User.builder()
                .username(username)
                .password(password)
                .roles(Collections.emptyList())
                .build());
    }

    @Test
    @DisplayName("should be equal when the same")
    void equals_thenTheSame_shouldReturnTrue() {
        User user1 = UserTestBuilder.aUser().build();
        User user2 = UserTestBuilder.from(user1).build();

        assertEquals(user1, user2);
    }

    @Test
    @DisplayName("should not be equal when username is different")
    void equals_whenUsernameIsDifferent_shouldReturnFalse() {
        User user1 = UserTestBuilder.aUser().build();
        User user2 = UserTestBuilder.from(user1).withUsername(TestData.username("different")).build();

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("should not be equal when roles are different")
    void equals_whenRolesAreDifferent_shouldReturnFalse() {
        User user1 = UserTestBuilder.aUser().build();
        User user2 = UserTestBuilder.from(user1).withRole(RoleTestBuilder.aRole().build()).build();

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("should not be equal when id is different")
    void equals_whenIdIsDifferent_shouldReturnFalse() {
        User user1 = UserTestBuilder.aUser().build();
        User user2 = UserTestBuilder.from(user1).withId(UserId.random()).build();

        assertNotEquals(user1, user2);
    }
}

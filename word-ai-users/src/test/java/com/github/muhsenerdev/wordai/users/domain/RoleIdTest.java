package com.github.muhsenerdev.wordai.users.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleIdTest {

    @Test
    @DisplayName("should create role id with valid value")
    void shouldCreateRoleIdWithValidValue() {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        RoleId roleId = RoleId.of(uuid);

        // Then
        assertThat(roleId).isNotNull();
        assertThat(roleId.getValue()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("should create random role id")
    void shouldCreateRandomRoleId() {
        // When
        RoleId roleId = RoleId.random();

        // Then
        assertThat(roleId).isNotNull();
        assertThat(roleId.getValue()).isNotNull();
    }

    @Test
    @DisplayName("should be equal when values are same")
    void shouldBeEqualWhenValuesAreSame() {
        // Given
        UUID uuid = UUID.randomUUID();
        RoleId roleId1 = RoleId.of(uuid);
        RoleId roleId2 = RoleId.of(uuid);

        // Then
        assertThat(roleId1).isEqualTo(roleId2);
        assertThat(roleId1.hashCode()).isEqualTo(roleId2.hashCode());
    }

    @Test
    @DisplayName("should not be equal when values are different")
    void shouldNotBeEqualWhenValuesAreDifferent() {
        // Given
        RoleId roleId1 = RoleId.random();
        RoleId roleId2 = RoleId.random();

        // Then
        assertThat(roleId1).isNotEqualTo(roleId2);
    }
}

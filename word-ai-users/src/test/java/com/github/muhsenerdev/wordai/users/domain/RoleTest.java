package com.github.muhsenerdev.wordai.users.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.RoleName;

class RoleTest {

    @Test
    @DisplayName("should create role with valid name")
    void shouldCreateRoleWithValidName() {
        // Given
        RoleName roleName = RoleName.of("ADMIN");

        // When
        Role role = Role.of(roleName);

        // Then
        assertThat(role).isNotNull();
        assertThat(role.getId()).isNotNull();
        assertThat(role.getName()).isEqualTo(roleName);
    }

    @Test
    @DisplayName("should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Given
        RoleName roleName = null;

        // When / Then
        assertThatThrownBy(() -> Role.of(roleName)).isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("Name of Role cannot be null.");
    }

}

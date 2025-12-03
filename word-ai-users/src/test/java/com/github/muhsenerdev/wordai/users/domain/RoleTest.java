package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThatThrownBy(() -> Role.of(roleName))
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("Name of Role cannot be null.");
    }

    @Test
    @DisplayName("should be equal when roles are same")
    void shouldBeEqualWhenRolesTheSame() {
        // Given
        RoleName roleName = RoleName.of("ADMIN");

        Role role1 = Role.of(roleName);
        Role role2 = RoleTestBuilder.from(role1).build();

        assertEquals(role1, role2);

    }

    @Test
    @DisplayName("should not be equal then ids are different")
    public void shouldNotBeEqual_whenIdsAreDiffrent() {
        // Given
        RoleName roleName = RoleName.of("ADMIN");

        Role role1 = Role.of(roleName);
        Role role2 = RoleTestBuilder.from(role1).withId(RoleId.random()).build();

        assertNotEquals(role1, role2);

    }

    @Test
    @DisplayName("should not be equal then names are different")
    public void shouldNotBeEqual_whenNamesAreDiffrent() {
        // Given
        RoleName roleName = RoleName.of("ADMIN");

        Role role1 = Role.of(roleName);
        Role role2 = RoleTestBuilder.from(role1).withName(TestData.randomRoleName()).build();

        assertNotEquals(role1, role2);

    }

}

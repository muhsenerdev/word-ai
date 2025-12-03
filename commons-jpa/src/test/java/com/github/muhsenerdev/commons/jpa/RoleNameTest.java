package com.github.muhsenerdev.commons.jpa;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("RoleName Tests")
class RoleNameTest {

    @Test
    @DisplayName("should create role name with valid value")
    void shouldCreateRoleNameWithValidValue() {
        // Given
        String value = "admin";

        // When
        RoleName roleName = RoleName.of(value);

        // Then
        assertThat(roleName).isNotNull();
        assertThat(roleName.getValue()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    @DisplayName("should create role name and trim whitespace")
    void shouldCreateRoleNameAndTrimWhitespace() {
        // Given
        String value = "  manager  ";

        // When
        RoleName roleName = RoleName.of(value);

        // Then
        assertThat(roleName).isNotNull();
        assertThat(roleName.getValue()).isEqualTo("ROLE_MANAGER");
    }

    @Test
    @DisplayName("should create role name when already starts with prefix")
    void shouldCreateRoleNameWhenAlreadyStartsWithPrefix() {
        // Given
        String value = "ROLE_USER";

        // When
        RoleName roleName = RoleName.of(value);

        // Then
        assertThat(roleName).isNotNull();
        assertThat(roleName.getValue()).isEqualTo("ROLE_USER");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "  " })
    @DisplayName("should throw exception when value is null or empty")
    void shouldThrowExceptionWhenValueIsNullOrEmpty(String value) {
        assertThatThrownBy(() -> RoleName.of(value))
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("Value of RoleName cannot be null or empty!");
    }

    @Test
    @DisplayName("should throw exception when value is too short")
    void shouldThrowExceptionWhenValueIsTooShort() {
        // Given
        String value = "a".repeat(RoleName.MIN_LENGTH - 1);

        // When / Then
        assertThatThrownBy(() -> RoleName.of(value))
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("RoleName must be between %d and %d characters long!"
                        .formatted(RoleName.MIN_LENGTH, RoleName.MAX_LENGTH));
    }

    @Test
    @DisplayName("should throw exception when value is too long")
    void shouldThrowExceptionWhenValueIsTooLong() {
        // Given
        String value = "a".repeat(RoleName.MAX_LENGTH + 1);

        // When / Then
        assertThatThrownBy(() -> RoleName.of(value))
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("RoleName must be between %d and %d characters long!"
                        .formatted(RoleName.MIN_LENGTH, RoleName.MAX_LENGTH));
    }

    @Test
    @DisplayName("should be equal when values are same")
    void shouldBeEqualWhenValuesAreSame() {
        // Given
        RoleName roleName1 = RoleName.of("admin");
        RoleName roleName2 = RoleName.of("ADMIN");

        // Then
        assertThat(roleName1).isEqualTo(roleName2);
        assertThat(roleName1.hashCode()).isEqualTo(roleName2.hashCode());
    }
}

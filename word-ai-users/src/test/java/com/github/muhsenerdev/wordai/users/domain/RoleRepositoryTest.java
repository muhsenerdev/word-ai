package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;
import com.github.muhsenerdev.commons.jpa.RoleName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("null")
public class RoleRepositoryTest extends BasePersistenceIT {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("should save role successfully")
    void shouldSaveRoleSuccessfully() {
        // Given
        Role role = RoleTestBuilder.aRole().build();

        // When
        Role savedRole = roleRepository.save(role);

        // Then
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo(role.getName());
    }

    @Test
    @DisplayName("should find role by name")
    void shouldFindRoleByName() {
        // Given
        Role role = RoleTestBuilder.aRole().build();
        roleRepository.save(role);

        // When
        Optional<Role> foundRole = roleRepository.findByName(role.getName());

        // Then
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getId()).isEqualTo(role.getId());
    }

    @Test
    @DisplayName("should not find role by non-existent name")
    void shouldNotFindRoleByNonExistentName() {
        // Given
        RoleName roleName = RoleName.of("NON_EXISTENT");

        // When
        Optional<Role> foundRole = roleRepository.findByName(roleName);

        // Then
        assertThat(foundRole).isEmpty();
    }

    @Test
    @DisplayName("should fail when saving duplicate role name")
    void shouldFailWhenSavingDuplicateRoleName() {
        // Given
        RoleName roleName = RoleName.of("ADMIN");
        Role role1 = RoleTestBuilder.aRole().withName(roleName).build();
        Role role2 = RoleTestBuilder.aRole().withName(roleName).build();

        roleRepository.saveAndFlush(role1);

        // When / Then
        assertThatThrownBy(() -> roleRepository.saveAndFlush(role2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}

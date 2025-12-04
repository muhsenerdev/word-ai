package com.github.muhsenerdev.wordai.users.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataAccessException;

import com.github.muhsenerdev.commons.jpa.Username;

@SuppressWarnings("null")
public class UserRepositoryTest extends BasePersistenceIT {

        @Autowired
        private UserRepository repository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private TestEntityManager entityManager;

        @Test
        public void should_setup_is_ok() {
                assertNotNull(repository);
                assertTrue(postgres.isRunning());
        }

        Role role;
        Role role2;

        @BeforeEach
        public void setup() {
                roleRepository.deleteAll();
                role = RoleTestBuilder.aRole().build();
                role2 = RoleTestBuilder.aRole().build();

                roleRepository.saveAll(Set.of(role, role2));

        }

        @Test
        public void should_soft_delete_user() {
                // Given
                roleRepository.saveAndFlush(role);

                User user = getUserWithRoles(role);
                repository.saveAndFlush(user);

                // When
                repository.delete(user);
                repository.flush();
                entityManager.clear(); // Clear cache to force DB hit

                // Then
                Optional<User> found = repository.findById(user.getId());
                assertFalse(found.isPresent(), "User should not be found via repository after delete");

                // Verify it still exists in DB (native query to bypass @SQLRestriction)
                Number count = (Number) entityManager.getEntityManager()
                                .createNativeQuery("SELECT COUNT(*) FROM users WHERE id = :id")
                                .setParameter("id", user.getId().getValue())
                                .getSingleResult();

                assertTrue(count.longValue() > 0, "User row should still exist in DB with deleted_at set");
        }

        private User getUserWithRoles(Role... roles) {
                return UserTestBuilder.aUser()
                                .withRoles(Set.of(roles))
                                .build();
        }

        @Test
        public void should_allow_reuse_username_after_soft_delete() {
                // Given

                User user1 = getUserWithRoles(role);
                repository.saveAndFlush(user1);
                repository.delete(user1);
                repository.flush();

                // When
                User user2 = UserTestBuilder.aUser()
                                .withUsername(user1.getUsername()) // Same username
                                .withRoles(Set.of(role))
                                .build();
                User savedUser2 = repository.saveAndFlush(user2);

                // Then
                assertNotNull(savedUser2.getId());
                assertTrue(repository.existsById(savedUser2.getId()));

                Optional<User> byUsername = repository.findByUsername(user2.getUsername());
                assertTrue(byUsername.isPresent());
        }

        @Test
        public void should_save_user_with_roles() {
                // Given

                User user = getUserWithRoles(role, role2);

                // When
                User savedUser = repository.saveAndFlush(user);
                entityManager.clear();

                // Then
                User foundUser = repository.findById(savedUser.getId()).orElseThrow();
                assertThat(foundUser.getRoles()).hasSize(2)
                                .extracting(Role::getName)
                                .containsExactlyInAnyOrder(role.getName(), role2.getName());
        }

        @Test
        void should_throw_exception_when_role_not_found() {
                // Given
                User user = UserTestBuilder.aUser().build();

                // When & Then
                assertThrows(DataAccessException.class, () -> repository.save(user));

        }

        @Test
        void existsByName_should_return_true() {
                // Given
                User user = getUserWithRoles(role);
                repository.saveAndFlush(user);

                // When
                boolean exists = repository.existsByUsername(user.getUsername());

                // Then
                assertTrue(exists);
        }

        @Test
        void existsByName_should_return_false() {
                // Given

                // When
                boolean exists = repository.existsByUsername(Username.of("non-existent"));

                // Then
                assertFalse(exists);
        }

        @Test
        void existsByUsername_should_filter_soft_deleted() {
                User user = getUserWithRoles(role);
                repository.save(user);
                repository.flush();
                repository.delete(user);
                repository.flush();
                entityManager.clear();

                boolean exists = repository.existsByUsername(user.getUsername());
                assertFalse(exists);
        }

}

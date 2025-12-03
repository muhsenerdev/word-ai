package com.github.muhsenerdev.wordai.users.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class UserRepositoryTest extends BasePersistenceIT {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_setup_is_ok() {
        assertNotNull(repository);
        assertTrue(postgres.isRunning());
    }

    @Test
    public void should_soft_delete_user() {
        // Given
        User user = UserTestBuilder.aUser().build();
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

    @Test
    public void should_allow_reuse_username_after_soft_delete() {
        // Given
        User user1 = UserTestBuilder.aUser().build();
        repository.saveAndFlush(user1);
        repository.delete(user1);
        repository.flush();

        // When
        User user2 = UserTestBuilder.aUser()
                .withUsername(user1.getUsername()) // Same username
                .build();
        User savedUser2 = repository.saveAndFlush(user2);

        // Then
        assertNotNull(savedUser2.getId());
        assertTrue(repository.existsById(savedUser2.getId()));

        Optional<User> byUsername = repository.findByUsername(user2.getUsername());
        assertTrue(byUsername.isPresent());
    }

}

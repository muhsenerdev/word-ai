package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;
import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.RoleRepository;
import com.github.muhsenerdev.wordai.users.domain.RoleTestBuilder;
import com.github.muhsenerdev.wordai.users.domain.User;
import com.github.muhsenerdev.wordai.users.domain.UserRepository;
import com.github.muhsenerdev.wordai.users.domain.UserTestBuilder;

import jakarta.persistence.EntityManager;

@EntityScan(basePackages = "com.github.muhsenerdev")
@EnableJpaRepositories(basePackages = "com.github.muhsenerdev")
@EnableJpaAuditing
@SuppressWarnings("null")
public class LearnerRepositoryTest extends BasePersistenceIT {

    @Autowired
    private LearnerRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("should setup is ok")
    public void should_setup_is_ok() {
        assertNotNull(repository);
        assertNotNull(userRepository);
    }

    private User saveUser() {
        Role role = RoleTestBuilder.aRole().build();
        roleRepository.save(role);

        User user = UserTestBuilder.aUser().withRoles(Set.of(role)).build();
        return userRepository.save(user);
    }

    @Test
    @DisplayName("should save and find learner by user id")
    public void should_save_and_find_learner_by_user_id() {
        // Given
        User user = saveUser();

        Learner learner = LearnerTestBuilder.aLearner().withUserId(user.getId()).build();

        // When
        repository.save(learner);

        // Then
        var foundLearner = repository.findByUserId(user.getId());
        assertThat(foundLearner).isPresent();
        assertThat(foundLearner.get().getId()).isEqualTo(user.getId());
        assertThat(foundLearner.get().getMotherLanguage()).isEqualTo(learner.getMotherLanguage());
        assertThat(foundLearner.get().getTargetLanguage()).isEqualTo(learner.getTargetLanguage());
    }

    @Test
    @DisplayName("learner should be soft-deleted")
    public void learner_should_be_soft_deleted() {
        // Given
        User user = saveUser();

        Learner learner = LearnerTestBuilder.aLearner().withUserId(user.getId()).build();

        // When
        repository.save(learner);
        repository.delete(learner);
        repository.flush();
        entityManager.clear();

        // Then
        var foundLearner = repository.findByUserId(user.getId());
        assertThat(foundLearner.isEmpty());
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser.isPresent());

        // Then
        // Verify it still exists in DB (native query to bypass @SQLRestrsiction)
        Object deletedAt = entityManager.createNativeQuery("SELECT deleted_at FROM learners WHERE user_id = :userId")
                .setParameter("userId", user.getId().getValue()).getSingleResult();
        assertThat(deletedAt).isNotNull();

    }
}

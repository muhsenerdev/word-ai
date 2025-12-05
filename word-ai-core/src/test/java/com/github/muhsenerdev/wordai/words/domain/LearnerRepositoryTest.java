package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

import jakarta.persistence.EntityManager;

@EntityScan(basePackages = { "com.github.muhsenerdev.wordai.words" })
@EnableJpaRepositories(basePackages = { "com.github.muhsenerdev.wordai.words" })
@EnableJpaAuditing
@SuppressWarnings("null")
public class LearnerRepositoryTest extends BasePersistenceIT {

    private static final UserId USER_ID = WordTestData.MOCK_USER_ID;
    @Autowired
    private LearnerRepository repository;

    // @Autowired
    // private UserRepository userRepository;

    // @Autowired
    // private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("should setup is ok")
    public void should_setup_is_ok() {
        // assertNotNull(repository);
        // assertNotNull(userRepository);
    }

    // private User saveUser() {
    // Role role = RoleTestBuilder.aRole().build();
    // roleRepository.save(role);

    // User user = UserTestBuilder.aUser().withRoles(Set.of(role)).build();
    // return userRepository.save(user);
    // }

    @Test
    @DisplayName("should save and find learner by user id")
    public void should_save_and_find_learner_by_user_id() {
        // Given
        // User user = saveUser();

        UserId userId = USER_ID;
        Learner learner = LearnerTestBuilder.aLearner().withUserId(userId).build();

        // When
        repository.save(learner);

        // Then
        var foundLearner = repository.findByUserId(userId);
        assertThat(foundLearner).isPresent();
        assertThat(foundLearner.get().getId()).isEqualTo(userId);
        assertThat(foundLearner.get().getMotherLanguage()).isEqualTo(learner.getMotherLanguage());
        assertThat(foundLearner.get().getTargetLanguage()).isEqualTo(learner.getTargetLanguage());
    }

    @Test
    @DisplayName("learner should be soft-deleted")
    public void learner_should_be_soft_deleted() {
        // Given

        Learner learner = LearnerTestBuilder.aLearner().withUserId(USER_ID).build();

        // When
        repository.save(learner);
        repository.delete(learner);
        repository.flush();
        entityManager.clear();

        // Then
        var foundLearner = repository.findByUserId(USER_ID);
        assertThat(foundLearner.isEmpty());

        // Then
        // Verify it still exists in DB (native query to bypass @SQLRestrsiction)
        Object deletedAt = entityManager.createNativeQuery("SELECT deleted_at FROM learners WHERE user_id = :userId")
                .setParameter("userId", USER_ID.getValue()).getSingleResult();
        assertThat(deletedAt).isNotNull();

    }
}

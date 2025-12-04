package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.UserId;

class LearnerTest {

    private UserId userId;
    private Language motherLanguage;
    private Language targetLanguage;

    @BeforeEach
    void setUp() {
        userId = UserId.random();
        motherLanguage = Language.ENGLISH;
        targetLanguage = Language.TURKISH;
    }

    @Test
    @DisplayName("Should create Learner successfully when all fields are valid")
    void shouldCreateLearnerSuccessfully() {
        // Given
        var learner = getLearner();

        // Then
        assertThat(learner).isNotNull();
        assertThat(learner.getId()).isEqualTo(userId);
        assertThat(learner.getMotherLanguage()).isEqualTo(motherLanguage);
        assertThat(learner.getTargetLanguage()).isEqualTo(targetLanguage);
    }

    @Test
    @DisplayName("Should throw exception when userId is null")
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Given
        userId = null;

        // When & Then
        assertThatThrownBy(() -> getLearner())
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("User id cannot be null");
    }

    @Test
    @DisplayName("Should throw exception when motherLanguage is null")
    void shouldThrowExceptionWhenMotherLanguageIsNull() {
        // Given
        motherLanguage = null;

        // When & Then
        assertThatThrownBy(() -> getLearner())
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("Mother language cannot be null");
    }

    @Test
    @DisplayName("Should throw exception when targetLanguage is null")
    void shouldThrowExceptionWhenTargetLanguageIsNull() {
        // Given
        targetLanguage = null;

        // When & Then
        assertThatThrownBy(() -> getLearner())
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("Target language cannot be null");
    }

    private Learner getLearner() {
        return Learner.of(userId, motherLanguage, targetLanguage);
    }

    @Test
    @DisplayName("Should throw exception when motherLanguage and targetLanguage are the same")
    void shouldThrowExceptionWhenLanguagesAreSame() {
        // Given
        motherLanguage = Language.ENGLISH;
        targetLanguage = Language.ENGLISH;

        // When & Then
        assertThatThrownBy(() -> getLearner())
                .isInstanceOf(InvalidDomainObjectException.class)
                .hasMessageContaining("Mother language and target language cannot be the same");
    }
}

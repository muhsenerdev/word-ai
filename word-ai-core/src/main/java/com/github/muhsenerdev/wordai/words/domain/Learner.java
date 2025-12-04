package com.github.muhsenerdev.wordai.words.domain;

import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;
import com.github.muhsenerdev.commons.jpa.UserId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "learners")
@SQLDelete(sql = "UPDATE learners SET deleted_at = CURRENT_TIMESTAMP WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@lombok.Getter
public class Learner extends SoftDeletableEntity<UserId> {

    public static final String LEARNER_USER_ID_REQUIRED = "learner.user_id.required";
    public static final String LEARNER_MOTHER_LANGUAGE_REQUIRED = "learner.mother_language.required";
    public static final String LEARNER_TARGET_LANGUAGE_REQUIRED = "learner.target_language.required";
    public static final String LEARNER_LANGUAGES_SAME = "learner.languages.same";

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    @Column(name = "mother_language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language motherLanguage;

    @Column(name = "target_language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language targetLanguage;

    protected Learner(UserId userId, Language motherLanguage, Language targetLanguage) {
        this.userId = userId;
        this.motherLanguage = motherLanguage;
        this.targetLanguage = targetLanguage;

        DomainUtils.notNull(userId, "User id cannot be null", "learner.user_id.required");
        DomainUtils.notNull(motherLanguage, "Mother language cannot be null", "learner.mother_language.required");
        DomainUtils.notNull(targetLanguage, "Target language cannot be null", "learner.target_language.required");

        if (Objects.equals(motherLanguage, targetLanguage)) {
            throw new InvalidDomainObjectException("Mother language and target language cannot be the same",
                    "learner.languages.same");
        }
    }

    public static Learner of(UserId userId, Language motherLanguage, Language targetLanguage) {
        return new Learner(userId, motherLanguage, targetLanguage);
    }

    @Override
    public UserId getId() {
        return userId;
    }
}

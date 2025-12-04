package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.jpa.UserId;

public class LearnerTestBuilder {

    private UserId userId;
    private Language motherLanguage;
    private Language targetLanguage;

    public LearnerTestBuilder() {
        this.userId = UserId.random();
        this.motherLanguage = Language.ENGLISH;
        this.targetLanguage = Language.TURKISH;
    }

    public static LearnerTestBuilder aLearner() {
        return new LearnerTestBuilder();
    }

    public static LearnerTestBuilder from(Learner learner) {
        return aLearner()
                .withUserId(learner.getId())
                .withMotherLanguage(learner.getMotherLanguage())
                .withTargetLanguage(learner.getTargetLanguage());
    }

    public LearnerTestBuilder withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public LearnerTestBuilder withMotherLanguage(Language motherLanguage) {
        this.motherLanguage = motherLanguage;
        return this;
    }

    public LearnerTestBuilder withTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
        return this;
    }

    public Learner build() {
        return new Learner(userId, motherLanguage, targetLanguage);
    }
}

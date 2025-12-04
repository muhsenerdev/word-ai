package com.github.muhsenerdev.wordai.words.support.data;

import java.util.UUID;

import com.github.muhsenerdev.wordai.words.application.CreateLearnerCommand;
import com.github.muhsenerdev.wordai.words.application.LearnerCreationResponse;

public class WordTestData {

    public static CreateLearnerCommand createLearnerCommand() {
        return CreateLearnerCommand.builder()
                .userId(UUID.randomUUID())
                .motherLanguage("ENGLISH")
                .targetLanguage("TURKISH")
                .build();
    }

    public static LearnerCreationResponse learnerCreationResponse() {
        return LearnerCreationResponse.builder()
                .userId(UUID.randomUUID())
                .build();
    }
}

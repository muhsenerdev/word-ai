package com.github.muhsenerdev.wordai.words.application;

public interface LearnerApplicationService {
    LearnerCreationResponse create(CreateLearnerCommand command);
}

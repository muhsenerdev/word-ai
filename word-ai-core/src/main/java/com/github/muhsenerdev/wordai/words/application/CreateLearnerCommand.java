package com.github.muhsenerdev.wordai.words.application;

import java.util.UUID;

import lombok.Builder;

@Builder(toBuilder = true)
public record CreateLearnerCommand(UUID userId, String motherLanguage, String targetLanguage) {
}

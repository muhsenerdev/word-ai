package com.github.muhsenerdev.wordai.words.application.onboarding;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;

@Builder(toBuilder = true)
public record OnboardingResponse(UUID userId, String username, String motherLanguage, String targetLanguage,
        Set<String> roles, String accessToken) {

}

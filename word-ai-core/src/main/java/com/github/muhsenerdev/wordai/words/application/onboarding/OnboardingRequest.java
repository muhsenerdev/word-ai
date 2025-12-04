package com.github.muhsenerdev.wordai.words.application.onboarding;

import lombok.Builder;

@Builder(toBuilder = true)
public record OnboardingRequest(String username, String password, String motherLanguage, String targetLanguage) {

}

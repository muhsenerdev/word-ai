package com.github.muhsenerdev.wordai.words.support.data;

import java.util.Set;
import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingRequest;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingResponse;

public class OnboardingTestData {

    public static Username username() {
        return Username.of("test-" + UUID.randomUUID().toString().substring(0, 10));
    }

    public static OnboardingRequest onboardingRequest() {
        return OnboardingRequest.builder().username(username().getValue()).password("StrongPassword123!")
                .motherLanguage("ENGLISH").targetLanguage("TURKISH").build();
    }

    public static OnboardingResponse onboardingResponse() {
        return OnboardingResponse.builder().userId(UUID.randomUUID()).username(username().getValue())
                .motherLanguage("ENGLISH").targetLanguage("TURKISH").roles(Set.of("USER"))
                .accessToken("test-access-token").build();
    }

    public static com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding.OnboardingInput onboardingInput() {
        return com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding.OnboardingInput.builder()
                .username(username().getValue()).password("StrongPassword123!").motherLanguage("ENGLISH")
                .targetLanguage("TURKISH").build();
    }

    public static com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding.OnboardingOutput onboardingOutput() {
        return com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding.OnboardingOutput.builder()
                .userId(UUID.randomUUID()).roles(Set.of("USER")).accessToken("test-access-token").build();
    }
}

package com.github.muhsenerdev.wordai.words.support.data;

import java.util.Set;
import java.util.UUID;

import com.github.muhsenerdev.wordai.users.support.data.TestData;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingRequest;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingResponse;

public class OnboardingTestData {

    public static OnboardingRequest onboardingRequest() {
        return OnboardingRequest.builder().username(TestData.username().getValue()).password("StrongPassword123!")
                .motherLanguage("ENGLISH").targetLanguage("TURKISH").build();
    }

    public static OnboardingResponse onboardingResponse() {
        return OnboardingResponse.builder().userId(UUID.randomUUID()).username(TestData.username().getValue())
                .motherLanguage("ENGLISH").targetLanguage("TURKISH").roles(Set.of("USER"))
                .accessToken("test-access-token").build();
    }

    public static com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.OnboardingInput onboardingInput() {
        return com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.OnboardingInput.builder()
                .username(TestData.username().getValue()).password("StrongPassword123!").motherLanguage("ENGLISH")
                .targetLanguage("TURKISH").build();
    }

    public static com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.OnboardingOutput onboardingOutput() {
        return com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.OnboardingOutput.builder()
                .userId(UUID.randomUUID()).roles(Set.of("USER")).accessToken("test-access-token").build();
    }
}

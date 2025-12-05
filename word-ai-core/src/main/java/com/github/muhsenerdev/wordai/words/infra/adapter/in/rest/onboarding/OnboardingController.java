package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingRequest;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingResponse;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication related endpoints")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @OnboardingDocs
    @PostMapping("/register")
    public ResponseEntity<OnboardingOutput> register(@jakarta.validation.Valid @RequestBody OnboardingInput input) {
        OnboardingRequest request = OnboardingRequest.builder().username(input.getUsername())
                .password(input.getPassword()).motherLanguage(input.getMotherLanguage())
                .targetLanguage(input.getTargetLanguage()).build();
        OnboardingResponse response = onboardingService.onboarding(request);
        ;

        OnboardingOutput output = OnboardingOutput.builder().userId(response.userId()).roles(response.roles())
                .accessToken(response.accessToken()).build();

        return ResponseEntity.ok(output);

    }

}

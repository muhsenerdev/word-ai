package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest;

import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingRequest;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingResponse;
import com.github.muhsenerdev.wordai.words.application.onboarding.OnboardingService;
import com.github.muhsenerdev.wordai.words.support.data.OnboardingTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingControllerTest {

    @Mock
    private OnboardingService onboardingService;

    @InjectMocks
    private OnboardingController onboardingController;

    @SuppressWarnings("null")
    @Test
    void should_register_successfully() {
        // Given
        OnboardingInput input = OnboardingTestData.onboardingInput();
        OnboardingResponse response = OnboardingTestData.onboardingResponse();

        when(onboardingService.onboarding(any(OnboardingRequest.class))).thenReturn(response);

        // When
        ResponseEntity<OnboardingOutput> result = onboardingController.register(input);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getUserId()).isEqualTo(response.userId());
        assertThat(result.getBody().getRoles()).isEqualTo(response.roles());
        assertThat(result.getBody().getAccessToken()).isEqualTo(response.accessToken());
    }

    @Test
    void should_fail_when_service_throws_exception() {
        // Given
        OnboardingInput input = OnboardingTestData.onboardingInput();
        when(onboardingService.onboarding(any(OnboardingRequest.class)))
                .thenThrow(new RuntimeException("Service failed"));

        // When
        Throwable throwable = catchThrowable(() -> onboardingController.register(input));

        // Then
        assertThat(throwable).isInstanceOf(RuntimeException.class)
                .hasMessage("Service failed");
    }
}

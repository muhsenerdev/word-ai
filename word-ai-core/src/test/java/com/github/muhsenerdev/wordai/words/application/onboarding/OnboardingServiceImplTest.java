package com.github.muhsenerdev.wordai.words.application.onboarding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.wordai.users.application.AuthenticationService;
import com.github.muhsenerdev.wordai.users.application.CreateUserCommand;
import com.github.muhsenerdev.wordai.users.application.GenerateTokenRequest;
import com.github.muhsenerdev.wordai.users.application.UserApplicationService;
import com.github.muhsenerdev.wordai.users.application.UserCreationResponse;
import com.github.muhsenerdev.wordai.words.application.CreateLearnerCommand;
import com.github.muhsenerdev.wordai.words.application.LearnerApplicationService;
import com.github.muhsenerdev.wordai.words.application.LearnerCreationResponse;
import com.github.muhsenerdev.wordai.words.support.data.OnboardingTestData;

@ExtendWith(MockitoExtension.class)
class OnboardingServiceImplTest {

        @InjectMocks
        private OnboardingServiceImpl onboardingService;

        @Mock
        private UserApplicationService userApplicationService;

        @Mock
        private LearnerApplicationService learnerApplicationService;

        @Mock
        private AuthenticationService authenticationService;

        private OnboardingRequest onboardingRequest;
        private UUID userId;
        private Set<String> roles;
        private String accessToken;

        private UserCreationResponse userCreationResponse;
        private LearnerCreationResponse learnerCreationResponse;
        private GenerateTokenRequest generateTokenRequest;

        @BeforeEach
        void setUp() {
                onboardingRequest = OnboardingTestData.onboardingRequest();
                userId = UUID.randomUUID();
                roles = Set.of("USER");
                accessToken = "generated-access-token";
                userCreationResponse = UserCreationResponse.builder().id(userId).username(onboardingRequest.username())
                                .roles(roles).build();
                learnerCreationResponse = LearnerCreationResponse.builder().userId(userId).build();
                generateTokenRequest = GenerateTokenRequest.builder().roles(roles).userId(userId)
                                .username(onboardingRequest.username())
                                .claims(Map.of("mother_language", onboardingRequest.motherLanguage(), "target_language",
                                                onboardingRequest.targetLanguage()))
                                .build();

        }

        @Test
        @DisplayName("should complete onboarding process when request is valid")
        void onboarding_shouldCompleteOnboardingProcess_whenRequestIsValid() {
                // Given
                when(userApplicationService.createUser(any(CreateUserCommand.class))).thenReturn(userCreationResponse);
                when(learnerApplicationService.create(any(CreateLearnerCommand.class)))
                                .thenReturn(learnerCreationResponse);
                when(authenticationService.generateAccessToken(any(GenerateTokenRequest.class)))
                                .thenReturn(accessToken);

                // When
                OnboardingResponse response = onboardingService.onboarding(onboardingRequest);

                // Then
                assertThat(response).isNotNull();
                assertThat(response.userId()).isEqualTo(userId);
                assertThat(response.username()).isEqualTo(onboardingRequest.username());
                assertThat(response.motherLanguage()).isEqualTo(onboardingRequest.motherLanguage());
                assertThat(response.targetLanguage()).isEqualTo(onboardingRequest.targetLanguage());
                assertThat(response.roles()).isEqualTo(roles);
                assertThat(response.accessToken()).isEqualTo(accessToken);

                verify(userApplicationService).createUser(any(CreateUserCommand.class));
                verify(learnerApplicationService).create(any(CreateLearnerCommand.class));
                verify(authenticationService).generateAccessToken(any(GenerateTokenRequest.class));
        }

        @Test
        @DisplayName("should throw exception when user creation fails")
        void onboarding_shouldThrowException_whenUserCreationFails() {
                // Given
                OnboardingRequest request = onboardingRequest;

                when(userApplicationService.createUser(any(CreateUserCommand.class)))
                                .thenThrow(new RuntimeException("User creation failed"));

                // When
                Throwable throwable = catchThrowable(() -> onboardingService.onboarding(request));

                // Then
                assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage("User creation failed");

                verify(userApplicationService).createUser(any(CreateUserCommand.class));
                verify(learnerApplicationService, never()).create(any(CreateLearnerCommand.class));
                verify(authenticationService, never()).generateAccessToken(any(GenerateTokenRequest.class));
        }

        @Test
        @DisplayName("should throw exception when learner creation fails")
        void onboarding_shouldThrowException_whenLearnerCreationFails() {
                // Given
                OnboardingRequest request = onboardingRequest;

                when(userApplicationService.createUser(any(CreateUserCommand.class))).thenReturn(userCreationResponse);
                doThrow(new RuntimeException("Learner creation failed")).when(learnerApplicationService)
                                .create(any(CreateLearnerCommand.class));

                // When
                Throwable throwable = catchThrowable(() -> onboardingService.onboarding(request));

                // Then
                assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage("Learner creation failed");

                verify(userApplicationService).createUser(any(CreateUserCommand.class));
                verify(learnerApplicationService).create(any(CreateLearnerCommand.class));
                verify(authenticationService, never()).generateAccessToken(any(GenerateTokenRequest.class));
        }

        @Test
        @DisplayName("should throw exception when token generation fails")
        void onboarding_shouldThrowException_whenTokenGenerationFails() {
                // Given
                OnboardingRequest request = onboardingRequest;

                when(userApplicationService.createUser(any(CreateUserCommand.class))).thenReturn(userCreationResponse);
                when(learnerApplicationService.create(any(CreateLearnerCommand.class)))
                                .thenReturn(learnerCreationResponse);
                when(authenticationService.generateAccessToken(any(GenerateTokenRequest.class)))
                                .thenThrow(new RuntimeException("Token generation failed"));

                // When
                Throwable throwable = catchThrowable(() -> onboardingService.onboarding(request));

                // Then
                assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage("Token generation failed");

                verify(userApplicationService).createUser(any(CreateUserCommand.class));
                verify(learnerApplicationService).create(any(CreateLearnerCommand.class));
                verify(authenticationService).generateAccessToken(any(GenerateTokenRequest.class));
        }
}

package com.github.muhsenerdev.wordai.words.application.onboarding;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.muhsenerdev.wordai.users.application.AuthenticationService;
import com.github.muhsenerdev.wordai.users.application.CreateUserCommand;
import com.github.muhsenerdev.wordai.users.application.GenerateTokenRequest;
import com.github.muhsenerdev.wordai.users.application.UserApplicationService;
import com.github.muhsenerdev.wordai.users.application.UserCreationResponse;
import com.github.muhsenerdev.wordai.words.application.CreateLearnerCommand;
import com.github.muhsenerdev.wordai.words.application.LearnerApplicationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

        private final UserApplicationService userApplicationService;
        private final LearnerApplicationService learnerApplicationService;
        private final AuthenticationService authenticationService;

        @Override
        @Transactional
        public OnboardingResponse onboarding(OnboardingRequest request) {
                // 1. Create User
                CreateUserCommand createUserCommand = CreateUserCommand.builder()
                                .username(request.username())
                                .password(request.password())
                                .build();
                UserCreationResponse creationResponse = userApplicationService.createUser(createUserCommand);
                UUID createdUserID = creationResponse.id();

                // 2. Create Learner
                CreateLearnerCommand learnerCommand = CreateLearnerCommand.builder()
                                .userId(createdUserID)
                                .motherLanguage(request.motherLanguage())
                                .targetLanguage(request.targetLanguage())
                                .build();
                learnerApplicationService.create(learnerCommand);

                // 3. Generate an access token to return it after successful onboarding.
                GenerateTokenRequest tokenRequest = GenerateTokenRequest.builder()
                                .roles(creationResponse.roles())
                                .userId(createdUserID)
                                .username(request.username())
                                .claims(Map.of(
                                                "mother_language", request.motherLanguage(),
                                                "target_language", request.targetLanguage()))
                                .build();
                ;
                String accessToken = authenticationService.generateAccessToken(tokenRequest);

                // 4. Build response and return
                return OnboardingResponse.builder()
                                .userId(createdUserID)
                                .username(request.username())
                                .motherLanguage(request.motherLanguage())
                                .targetLanguage(request.targetLanguage())
                                .roles(creationResponse.roles())
                                .accessToken(accessToken)
                                .build();

        }

}

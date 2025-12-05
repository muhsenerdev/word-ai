package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class OnboardingOutput {

    private UUID userId;
    private Set<String> roles;

    @JsonProperty("access_token")
    private String accessToken;

}

package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.muhsenerdev.commons.jpa.Username;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Schema(description = "Input data for onboarding a new user")
public class OnboardingInput {

    @NotBlank(message = "username.required")
    @Size(min = Username.MIN_LENGTH, max = Username.MAX_LENGTH, message = "username.size")
    @Schema(description = "The username for the new account", example = "johndoe")
    private String username;

    @NotBlank(message = "password.required")
    @Schema(description = "The password for the new account", example = "SecureP@ssw0rd")
    private String password;

    @NotBlank(message = "mother_language.required")
    @SupportedLanguage(message = "mother_language.unsupported")
    @JsonProperty("mother_language")
    @Schema(description = "The user's native language", example = "TURKISH")
    private String motherLanguage;

    @NotBlank(message = "target_language.required")
    @SupportedLanguage(message = "target_language.unsupported")
    @JsonProperty("target_language")
    @Schema(description = "The language the user wants to learn", example = "ENGLISH")
    private String targetLanguage;

}

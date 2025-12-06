package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.muhsenerdev.commons.core.exception.DuplicateResourceException;
import com.github.muhsenerdev.wordai.users.application.UserCreationResponse;
import com.github.muhsenerdev.wordai.words.domain.Learner;
import com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.shared.WordsBaseIT;
import com.github.muhsenerdev.wordai.words.support.data.OnboardingTestData;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

class OnboardingControllerIT extends WordsBaseIT {

	private static final String PATH = "/api/v1/auth/register";

	@Test
	void should_return_409_when_username_is_taken() throws Exception {
		// Given
		OnboardingInput input = OnboardingTestData.onboardingInput();
		when(userApplicationService.createUser(any()))
				.thenThrow(new DuplicateResourceException("user", "username", input.getUsername()));

		// When & Then
		mockMvc.perform(
				post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isConflict());

	}

	@Test
	void should_return_400_when_validation_fails() throws Exception {
		// Given
		OnboardingInput input = OnboardingTestData.onboardingInput().toBuilder().username("") // Invalid username
				.password("").motherLanguage(null).targetLanguage(null).build();

		// When & Then
		MvcResult result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest()).andReturn();
		;

		JsonNode node = extractNode(result);

		System.out.println(node.get("errors"));
	}

	@Test
	void should_return_400_when_mother_and_target_language_are_same() throws Exception {
		// Given
		OnboardingInput input = OnboardingTestData.onboardingInput().toBuilder().motherLanguage("ENGLISH")
				.targetLanguage("ENGLISH").build();

		when(userApplicationService.createUser(any())).thenReturn(UserCreationResponse.builder()
				.username(input.getUsername()).id(WordTestData.MOCK_USER_ID.getValue()).roles(Set.of()).build());

		// When & Then
		MvcResult result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest()).andReturn();

		;
		JsonNode jsonNode = extractNode(result);
		assertThat(jsonNode.get("errors").get(Learner.LEARNER_LANGUAGES_SAME).asText()).isNotEmpty();

	}

}

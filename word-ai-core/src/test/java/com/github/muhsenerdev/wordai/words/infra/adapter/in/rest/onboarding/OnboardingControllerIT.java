package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.wordai.words.WordsTestApplication;
import com.github.muhsenerdev.wordai.words.domain.Learner;
import com.github.muhsenerdev.wordai.words.support.data.OnboardingTestData;

@SpringBootTest(classes = WordsTestApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@EntityScan(basePackages = "com.github.muhsenerdev")
@EnableJpaRepositories(basePackages = "com.github.muhsenerdev")
@ComponentScan(basePackages = "com.github.muhsenerdev")
@ActiveProfiles("test")
@SuppressWarnings("null")
class OnboardingControllerIT {

	private static final String PATH = "/api/v1/auth/register";

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17").withDatabaseName("word_ai")
			.withUsername("postgres").withPassword("postgres");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void should_return_409_when_username_is_taken() throws Exception {
		// Given
		OnboardingInput input = OnboardingTestData.onboardingInput();

		// Register first time (success)
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk());

		// When & Then - Register second time (conflict)
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isConflict());
	}

	@Test
	void should_return_400_when_validation_fails() throws Exception {
		// Given
		OnboardingInput input = OnboardingTestData.onboardingInput().toBuilder().username("") // Invalid username
				.password("").motherLanguage(null).targetLanguage(null).build();

		// When & Then
		MvcResult result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
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

		// When & Then
		MvcResult result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest()).andReturn();

		;
		JsonNode jsonNode = extractNode(result);
		assertThat(jsonNode.get("errors").get(Learner.LEARNER_LANGUAGES_SAME).asText()).isNotEmpty();

	}

	private JsonNode extractNode(MvcResult result) throws IOException {
		return objectMapper.readTree(result.getResponse().getContentAsByteArray());
	}
}

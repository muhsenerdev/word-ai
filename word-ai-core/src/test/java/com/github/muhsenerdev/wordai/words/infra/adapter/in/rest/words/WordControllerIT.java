package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.wordai.words.WordsTestApplication;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

@SpringBootTest(classes = WordsTestApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@EntityScan(basePackages = "com.github.muhsenerdev")
@EnableJpaRepositories(basePackages = "com.github.muhsenerdev")
@ComponentScan(basePackages = "com.github.muhsenerdev")
@ActiveProfiles("test")
@SuppressWarnings("null")
class WordControllerIT {

	private static final String PATH = "/api/v1/words/bulk";

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17").withDatabaseName("word_ai")
			.withUsername("postgres").withPassword("postgres");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser(roles = "ADMIN")
	void should_return_200_when_bulk_insert_is_successful() throws Exception {
		// Given
		int count = 2;
		BulkInsertWordInput input = WordTestData.bulkInsertWordInput(count);

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.responses", hasSize(count)));
	}

	@Test
	void should_return_401_when_user_is_not_authenticated() throws Exception {
		// Given
		BulkInsertWordInput input = WordTestData.bulkInsertWordInput(1);

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(roles = "USER")
	void should_return_403_when_user_is_not_admin() throws Exception {
		// Given
		BulkInsertWordInput input = WordTestData.bulkInsertWordInput(1);

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void should_return_400_when_input_is_invalid() throws Exception {
		// Given
		BulkInsertWordInput input = WordTestData.bulkInsertWordInput(1).toBuilder().words(java.util.Collections.emptyList()) // Invalid:
																																																													// empty
																																																													// list
				.build();

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors['words.required']").value("words.required"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void should_return_400_when_language_is_invalid() throws Exception {
		// Given
		var wordInput = WordTestData.insertWordInput().toBuilder().language("INVALID_LANG").build();
		var input = BulkInsertWordInput.builder().words(java.util.List.of(wordInput)).build();

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void should_return_400_when_cefr_level_is_unknown() throws Exception {
		// Given
		var wordInput = WordTestData.insertWordInput().toBuilder().cefrLevel("INVALID_CEFR").build();
		var input = BulkInsertWordInput.builder().words(java.util.List.of(wordInput)).build();

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void should_return_400_when_part_of_speech_is_invalid() throws Exception {
		// Given
		var wordInput = WordTestData.insertWordInput().toBuilder().partOfSpeech("INVALID_POS").build();
		var input = BulkInsertWordInput.builder().words(java.util.List.of(wordInput)).build();

		// When & Then
		mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}
}

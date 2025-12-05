package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.muhsenerdev.wordai.words.application.words.WordsInsertedListener;
import com.github.muhsenerdev.wordai.words.domain.WordId;
import com.github.muhsenerdev.wordai.words.domain.WordRepository;
import com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.shared.WordsBaseIT;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

class WordControllerIT extends WordsBaseIT {

	private static final String PATH = "/api/v1/words/bulk";

	@Autowired
	private WordRepository wordRepository;

	@MockitoBean
	public WordsInsertedListener mockListener; // To prevent it from executing.

	@Test
	@WithMockUser(roles = "ADMIN")
	void should_return_200_when_bulk_insert_is_successful() throws Exception {
		// Given
		int count = 2;
		BulkInsertWordInput input = WordTestData.bulkInsertWordInput(count);

		// When & Then
		MvcResult result = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.responses", hasSize(count))).andReturn();
		;

		verify(mockListener).onWordsInserted(any());

		JsonNode nodObject = super.objectMapper.readTree(result.getResponse().getContentAsByteArray());
		List<WordId> wordIds = nodObject.get("responses").valueStream()
				.map(v -> WordId.of(UUID.fromString(v.get("id").asText()))).toList();

		wordIds.forEach(wordId -> {
			assertThat(wordRepository.existsById(wordId)).isTrue();
		});

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

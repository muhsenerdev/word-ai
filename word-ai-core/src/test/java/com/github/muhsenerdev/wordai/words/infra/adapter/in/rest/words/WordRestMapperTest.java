package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.muhsenerdev.wordai.words.application.words.BulkInsertWordCommand;
import com.github.muhsenerdev.wordai.words.application.words.BulkWordInsertionResponse;
import com.github.muhsenerdev.wordai.words.application.words.InsertWordCommand;
import com.github.muhsenerdev.wordai.words.application.words.WordInsertionResponse;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WordRestMapperTest.TestConfig.class })
class WordRestMapperTest {

	@Autowired
	private WordRestMapper mapper;

	private UUID id;
	private WordInsertionResponse wordInsertionResponse;
	private BulkWordInsertionResponse bulkWordInsertionResponse;

	@BeforeEach
	public void setup() {
		id = UUID.randomUUID();
		wordInsertionResponse = WordInsertionResponse.builder().id(id).build();
		bulkWordInsertionResponse = BulkWordInsertionResponse.builder()
				.responses(Collections.singletonList(wordInsertionResponse)).build();
	}

	@Test
	void shouldMapInsertWordInputToInsertWordCommand() {
		// Given
		InsertWordInput input = WordTestData.insertWordInput();

		// When
		InsertWordCommand command = mapper.toInsertCommand(input);

		// Then
		assertThat(command).isNotNull();
		assertThat(command.text()).isEqualTo(input.getText());
		assertThat(command.partOfSpeech()).isEqualTo(input.getPartOfSpeech());
		assertThat(command.cefrLevel()).isEqualTo(input.getCefrLevel());
		assertThat(command.language()).isEqualTo(input.getLanguage());
	}

	@Test
	void shouldMapBulkInsertWordInputToBulkInsertWordCommand() {
		// Given
		int count = 2;
		BulkInsertWordInput input = WordTestData.bulkInsertWordInput(count);

		// When
		BulkInsertWordCommand command = mapper.toBulkInsertWordCommand(input);

		// Then
		assertThat(command).isNotNull();
		assertThat(command.commands()).hasSize(count);
		assertThat(command.commands().get(0).text()).isEqualTo(input.getWords().get(0).getText());
	}

	@Test
	void shouldMapWordInsertionResponseToWordInsertionOutput() {
		// Given
		WordInsertionResponse response = wordInsertionResponse;

		// When
		WordInsertionOutput output = mapper.toWordInsertionOutput(response);

		// Then
		assertThat(output).isNotNull();
		assertThat(output.getId()).isEqualTo(id);
	}

	@Test
	void shouldMapBulkWordInsertionResponseToBulkWordInsertionOutput() {
		// Given
		BulkWordInsertionResponse response = bulkWordInsertionResponse;

		// When
		BulkWordInsertionOutput output = mapper.toBulkWordInsertionOutput(response);

		// Then
		assertThat(output).isNotNull();
		assertThat(output.getResponses()).hasSize(1);
		assertThat(output.getResponses().get(0).getId()).isEqualTo(id);
	}

	@TestConfiguration
	static class TestConfig {
		@Bean
		public WordRestMapper wordRestMapper() {
			return new WordRestMapper();
		}
	}
}

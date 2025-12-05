package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.genai.domain.prompt.PromptCreationData;
import com.github.muhsenerdev.genai.support.data.PromptTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PromptMapperTest {

	private PromptMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new PromptMapper();
	}

	@Test
	@DisplayName("should map CreatePromptCommand to PromptCreationData")
	void shouldMapCommandToCreationData() {
		CreatePromptCommand command = PromptTestData.aCreatePromptCommand();

		PromptCreationData creationData = mapper.toCreationData(command);

		assertThat(creationData).isNotNull();
		assertThat(creationData.name()).isEqualTo(command.name());
		assertThat(creationData.slug()).isEqualTo(command.slug());
		assertThat(creationData.provider()).isEqualTo(command.provider());
		assertThat(creationData.model()).isEqualTo(command.model());
		assertThat(creationData.systemMessage()).isEqualTo(command.systemMessage());
		assertThat(creationData.userMessageTemplate()).isEqualTo(command.userMessageTemplate());
		assertThat(creationData.inputSchema()).isEqualTo(command.inputSchema());
		assertThat(creationData.outputSchema()).isEqualTo(command.outputSchema());
		assertThat(creationData.modelOptions()).isEqualTo(command.modelOptions());
		assertThat(creationData.outputType()).isEqualTo(command.outputType());
	}

	@Test
	@DisplayName("should return null when CreatePromptCommand is null")
	void shouldReturnNullWhenCommandIsNull() {
		PromptCreationData creationData = mapper.toCreationData(null);

		assertThat(creationData).isNull();
	}
}

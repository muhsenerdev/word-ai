package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.genai.application.prompt.CreatePromptCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptCreationResponse;
import com.github.muhsenerdev.genai.support.config.TestBeans;
import com.github.muhsenerdev.genai.support.data.PromptTestData;

class PromptWebMapperTest {

	private static final PromptWebMapper mapper = TestBeans.promptWebMapper();

	@Test
	@DisplayName("should map CreatePromptInput to CreatePromptCommand")
	void shouldMapInputToCommand() {
		CreatePromptInput input = PromptTestData.aCreatePromptInput();

		CreatePromptCommand command = mapper.toCreateCommand(input);

		assertThat(command).isNotNull();
		assertThat(command.name()).isEqualTo(input.getName());
		assertThat(command.slug().getValue()).isEqualTo(input.getSlug());
		assertThat(command.provider().name()).isEqualTo(input.getProvider());
		assertThat(command.model()).isEqualTo(input.getModel());
		assertThat(command.systemMessage()).isEqualTo(input.getSystemMessage());
		assertThat(command.userMessageTemplate()).isEqualTo(input.getUserMessageTemplate());
		assertThat(command.inputSchema().getValue()).isEqualTo(input.getInputSchema());
		assertThat(command.outputSchema().getValue()).isEqualTo(input.getOutputSchema());
		assertThat(command.modelOptions()).isEqualTo(input.getModelOptions());
		assertThat(command.outputType().name()).isEqualTo(input.getOutputType());
	}

	@Test
	@DisplayName("should map PromptCreationResponse to PromptCreationOutput")
	void shouldMapResponseToOutput() {
		PromptCreationResponse response = PromptTestData.aPromptCreationResponse();

		PromptCreationOutput output = mapper.toCreationOutput(response);

		assertThat(output).isNotNull();
		assertThat(output.getId()).isEqualTo(response.id().getValue());
		assertThat(output.getSlug()).isEqualTo(response.slug().getValue());
	}

	@Test
	@DisplayName("should return null when CreatePromptInput is null")
	void shouldReturnNullWhenInputIsNull() {
		assertThat(mapper.toCreateCommand(null)).isNull();
	}

	@Test
	@DisplayName("should return null when PromptCreationResponse is null")
	void shouldReturnNullWhenResponseIsNull() {
		assertThat(mapper.toCreationOutput(null)).isNull();
	}

	@Test
	@DisplayName("should not throw NPE when input fields are null")
	void shouldNotThrowNpeWhenInputFieldsAreNull() {
		CreatePromptInput input = new CreatePromptInput();

		CreatePromptCommand command = mapper.toCreateCommand(input);

		assertThat(command).isNotNull();
		assertThat(command.name()).isNull();
		assertThat(command.slug()).isNull();
		assertThat(command.provider()).isNull();
		assertThat(command.model()).isNull();
		assertThat(command.systemMessage()).isNull();
		assertThat(command.userMessageTemplate()).isNull();
		assertThat(command.inputSchema()).isNull();
		assertThat(command.outputSchema()).isNull();
		assertThat(command.modelOptions()).isNull();
		assertThat(command.outputType()).isNull();
	}
}

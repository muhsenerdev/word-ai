package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.core.DomainException;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.genai.application.prompt.CreatePromptCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptCreationResponse;
import com.github.muhsenerdev.genai.application.prompt.PromptVoMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PromptWebMapper {

	private final PromptVoMapper voMapper;

	public CreatePromptCommand toCreateCommand(CreatePromptInput input) {
		if (input == null)
			return null;

		try {
			return CreatePromptCommand.builder().name(input.getName()).slug(voMapper.toSlug(input.getSlug()))

					.provider(voMapper.toLLMProvider(input.getProvider())).model(input.getModel())
					.systemMessage(input.getSystemMessage()).userMessageTemplate(input.getUserMessageTemplate())
					.inputSchema(voMapper.toPayloadSchema(input.getInputSchema()))
					.outputSchema(voMapper.toPayloadSchema(input.getOutputSchema())).modelOptions(input.getModelOptions())
					.outputType(voMapper.toOutputType(input.getOutputType())).build();
		} catch (DomainException d) {
			throw new BusinessValidationException(d.getMessage(), d.getErrorCode(), d);
		}

	}

	public PromptCreationOutput toCreationOutput(PromptCreationResponse response) {
		if (response == null) {
			return null;
		}

		return PromptCreationOutput.builder().id(voMapper.fromId(response.id()))
				.slug(voMapper.fromSingleVO(response.slug())).build();

	}
}

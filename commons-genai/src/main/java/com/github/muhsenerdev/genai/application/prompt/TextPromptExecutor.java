package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchema;
import com.github.muhsenerdev.genai.domain.prompt.PayloadValidator;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TextPromptExecutor implements PromptExecutorStrategy {

	private final PayloadValidator inputValidator;
	private final ChatClient chatClient;
	private final ObjectMapper objectMapper;

	@Override
	public boolean supports(PromptOutputType outputType) {
		return Objects.equals(PromptOutputType.TEXT, outputType);
	}

	@Override
	public AiResponse execute(PromptDefinition def, Map<String, Object> inputVariables)
			throws PromptExecutionException, IllegalArgumentException {
		Assert.notNull(def, "Prompt def cannot be null");
		Assert.notNull(inputVariables, "inputVariable cannot be null");
		try {

			// 1. Check output type supported
			PromptOutputType outputType = def.getOutputType();
			if (!supports(outputType))
				throw new PromptExecutionException("Unsupported prompt output type: " + outputType);

			// 2. Validate & Render Input variables to user message, If schema exist
			String userMessage = renderUserMessage(def, inputVariables);

			// 3. Append output instruction if output schema exists
			String finalUserMessage = appendOutputInstruction(userMessage, def);

			String jsonResponse = chatClient.prompt().system(def.getSystemMessage()).user(finalUserMessage).call().content();

			assert jsonResponse != null;
			String cleanJson = jsonResponse.replace("```json", "").replace("```", "").trim();
			Map<String, Object> output = objectMapper.readValue(cleanJson, new TypeReference<Map<String, Object>>() {
			});

			return TextResponse.builder().outputMap(output).build();

		} catch (Exception e) {
			throw new PromptExecutionException(
					"Failed to execute prompt with slug: %s, due to :%s".formatted(def.getSlug(), e.getMessage()), e);
		}
	}

	private @NotNull String prepareUserMessage(PromptDefinition promptDef, Map<String, Object> inputMap) {
		String userMessage = renderUserMessage(promptDef, inputMap);
		return appendOutputInstruction(userMessage, promptDef);
	}

	// It validates and renders input variable to the user message template
	// If input schema exists!
	private String renderUserMessage(PromptDefinition promptDef, Map<String, Object> inputMap) {
		PayloadSchema inputSchema = promptDef.getInputSchema();
		if (inputSchema == null)
			return promptDef.getUserMessageTemplate();

		inputValidator.validateOrThrow(inputMap, inputSchema);
		PromptTemplate promptTemplate = new PromptTemplate(promptDef.getUserMessageTemplate());
		return promptTemplate.render(inputMap);

	}

	// It appends output instruction to the userMessage, If outputSchema exists!
	private @NotNull String appendOutputInstruction(String userMessage, PromptDefinition promptDef) {
		PayloadSchema outputSchema = promptDef.getOutputSchema();
		if (outputSchema == null)
			return userMessage;

		String jsonSchemaString = outputSchema.getValue().toPrettyString();
		String schemaInstruction = String.format("""

				IMPORTANT: Provide the output strictly in JSON format.
				Do not include any explanation or markdown formatting (like ```json).
				The output must validate against the following JSON Schema:
				%s
				""", jsonSchemaString);

		return userMessage + schemaInstruction;
	}
}

package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Data
@Schema(description = "Input payload for creating a new prompt configuration")
public class CreatePromptInput {

	@NotBlank(message = "prompt.name.required")
	@Schema(description = "The human-readable name of the prompt", example = "Summarize Text")
	private String name;

	@NotBlank(message = "prompt.slug.required")
	@Schema(description = "Unique identifier slug for the prompt", example = "summarize-text")
	private String slug;

	@NotBlank(message = "prompt.provider.required")
	@Schema(description = "AI provider name (e.g., openai, anthropic)", example = "openai")
	private String provider;

	@NotBlank(message = "prompt.model.required")
	@Schema(description = "Model identifier to be used", example = "gpt-4o")
	private String model;

	@JsonProperty("system_message")
	@Schema(description = "System message/instruction for the AI", example = "You are a helpful assistant that summarizes text.")
	private String systemMessage;

	@NotBlank(message = "prompt.user_message_template.required")
	@JsonProperty("user_message_template")
	@Schema(description = "Template for the user message with placeholders", example = "Please summarize the following text: {{text}}")
	private String userMessageTemplate;

	@JsonProperty("input_schema")
	@Schema(description = "JSON schema defining the expected input variables")
	private JsonNode inputSchema;

	@JsonProperty("output_schema")
	@Schema(description = "JSON schema defining the structured output format (if applicable)")
	private JsonNode outputSchema;

	@JsonProperty("model_options")
	@Schema(description = "Additional options/parameters for the model (temperature, etc.)")
	private Map<String, Object> modelOptions;

	@JsonProperty("output_type")
	@Schema(description = "Type of output expected (e.g., TEXT, JSON)", example = "TEXT", allowableValues = { "JSON",
			"TEXT", "IMAGE" })
	@NotBlank(message = "prompt.output_type.required")
	private String outputType;

}

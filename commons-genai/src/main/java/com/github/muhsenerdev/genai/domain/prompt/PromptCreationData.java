package com.github.muhsenerdev.genai.domain.prompt;

import java.util.Map;

import com.github.muhsenerdev.commons.jpa.Slug;

import lombok.Builder;

public record PromptCreationData(String name, Slug slug, LLMProvider provider, String model, String systemMessage,
		String userMessageTemplate, PayloadSchema inputSchema, PayloadSchema outputSchema, Map<String, Object> modelOptions,
		PromptOutputType outputType) {

	@Builder(toBuilder = true)
	public PromptCreationData(String name, Slug slug, LLMProvider provider, String model, String systemMessage,
			String userMessageTemplate, PayloadSchema inputSchema, PayloadSchema outputSchema,
			Map<String, Object> modelOptions, PromptOutputType outputType) {
		this.name = name;
		this.slug = slug;
		this.provider = provider;
		this.model = model;
		this.systemMessage = systemMessage;
		this.userMessageTemplate = userMessageTemplate;
		this.inputSchema = inputSchema;
		this.outputSchema = outputSchema;
		this.modelOptions = modelOptions;
		this.outputType = outputType;
	}
}

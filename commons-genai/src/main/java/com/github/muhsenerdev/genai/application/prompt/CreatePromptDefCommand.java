package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.genai.domain.prompt.LLMProvider;
import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprint;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;
import com.github.muhsenerdev.commons.jpa.*;
import lombok.Builder;

import java.util.Map;

public record CreatePromptDefCommand(String name, Slug slug, LLMProvider provider, String model, String systemMessage,
        String userMessageTemplate, PayloadBlueprint inputSchema, PayloadBlueprint outputSchema,
        Map<String, Object> modelOptions, PromptOutputType outputType) {

    @Builder(toBuilder = true)
    public CreatePromptDefCommand(String name, Slug slug, LLMProvider provider, String model, String systemMessage,
            String userMessageTemplate, PayloadBlueprint inputSchema, PayloadBlueprint outputSchema,
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

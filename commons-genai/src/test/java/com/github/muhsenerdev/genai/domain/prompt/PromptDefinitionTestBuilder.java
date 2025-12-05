package com.github.muhsenerdev.genai.domain.prompt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.support.data.PromptTestData;
import com.github.muhsenerdev.genai.support.data.TestData;

public class PromptDefinitionTestBuilder {
    private PromptId id = PromptId.random();
    private String name = "Test Prompt " + UUID.randomUUID().toString().substring(0, 8);
    private Slug slug = TestData.slug();
    private LLMProvider provider = LLMProvider.OPEN_AI;
    private String model = "test-model";
    private String systemMessage = "You are a helpful assistant.";
    private String userMessageTemplate = "Hello, {name}!";
    private PayloadSchema inputSchema = PromptTestData.payloadSchema();
    private PayloadSchema outputSchema = PromptTestData.payloadSchema();
    private Map<String, Object> modelOptions = new HashMap<>();
    private PromptOutputType outputType = PromptOutputType.TEXT;

    private PromptDefinitionTestBuilder() {
        // Initialize default values for Java types
        modelOptions.put("temperature", 0.7);
        modelOptions.put("max_tokens", 1000);
    }

    public static PromptDefinitionTestBuilder aPromptDefinition() {
        return new PromptDefinitionTestBuilder();
    }

    public static PromptDefinitionTestBuilder from(PromptDefinition promptDefinition) {
        Assert.notNull(promptDefinition, "PromptDefinition cannot be null");

        return aPromptDefinition().withId(promptDefinition.getId()).withName(promptDefinition.getName())
                .withSlug(promptDefinition.getSlug()).withProvider(promptDefinition.getProvider())
                .withModel(promptDefinition.getModel()).withSystemMessage(promptDefinition.getSystemMessage())
                .withUserMessageTemplate(promptDefinition.getUserMessageTemplate())
                .withInputSchema(promptDefinition.getInputSchema()).withOutputSchema(promptDefinition.getOutputSchema())
                .withModelOptions(promptDefinition.getModelOptions());
    }

    public PromptDefinitionTestBuilder withId(PromptId id) {
        this.id = id;
        return this;
    }

    public PromptDefinitionTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PromptDefinitionTestBuilder withSlug(Slug slug) {
        this.slug = slug;
        return this;
    }

    public PromptDefinitionTestBuilder withProvider(LLMProvider provider) {
        this.provider = provider;
        return this;
    }

    public PromptDefinitionTestBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public PromptDefinitionTestBuilder withSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
        return this;
    }

    public PromptDefinitionTestBuilder withUserMessageTemplate(String userMessageTemplate) {
        this.userMessageTemplate = userMessageTemplate;
        return this;
    }

    public PromptDefinitionTestBuilder withInputSchema(PayloadSchema inputSchema) {
        this.inputSchema = inputSchema;
        return this;
    }

    public PromptDefinitionTestBuilder withOutputSchema(PayloadSchema outputSchema) {
        this.outputSchema = outputSchema;
        return this;
    }

    public PromptDefinitionTestBuilder withModelOptions(Map<String, Object> modelOptions) {
        this.modelOptions = modelOptions != null ? new HashMap<>(modelOptions) : null;
        return this;
    }

    public PromptDefinitionTestBuilder withOutputType(PromptOutputType outputType) {
        this.outputType = outputType;
        return this;
    }

    public PromptDefinition build() {
        return new PromptDefinition(id, name, slug, provider, model, systemMessage, userMessageTemplate, inputSchema,
                outputSchema, modelOptions, outputType);
    }
}

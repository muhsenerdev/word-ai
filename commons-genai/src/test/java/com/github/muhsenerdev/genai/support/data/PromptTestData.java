package com.github.muhsenerdev.genai.support.data;

import java.util.HashMap;
import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.application.prompt.CreatePromptCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptCreationResponse;
import com.github.muhsenerdev.genai.domain.prompt.LLMProvider;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchema;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchemaFactory;
import com.github.muhsenerdev.genai.domain.prompt.PromptCreationData;
import com.github.muhsenerdev.genai.domain.prompt.PromptId;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;
import com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt.CreatePromptInput;
import com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt.PromptCreationOutput;
import com.github.muhsenerdev.genai.support.config.TestBeans;

public class PromptTestData {

    private static final PayloadSchemaFactory SCHEMA_FACTORY = TestBeans.payloadSchemaFactory();
    private static final com.fasterxml.jackson.databind.ObjectMapper OBJECT_MAPPER = new com.fasterxml.jackson.databind.ObjectMapper();
    private static final com.fasterxml.jackson.databind.JsonNode JSON_SCHEMA_NODE;;

    static {
        try {
            JSON_SCHEMA_NODE = OBJECT_MAPPER
                    .readTree("{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}}}");
        } catch (java.io.IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static PayloadSchema payloadSchema() {
        return SCHEMA_FACTORY.create(JSON_SCHEMA_NODE);
    }

    public static Slug slug() {
        return Slug.of("test-slug-" + UUID.randomUUID().toString().substring(0, 8));
    }

    public static PromptCreationData aPromptCreationData() {
        return PromptCreationData.builder().name("Test Prompt").slug(slug()).provider(LLMProvider.OPEN_AI)
                .model("gpt-3.5-turbo").systemMessage("You are a helpful assistant.")
                .userMessageTemplate("Hello, {name}!").inputSchema(payloadSchema()).outputSchema(payloadSchema())
                .outputType(PromptOutputType.TEXT).modelOptions(new HashMap<>()).build();
    }

    public static CreatePromptCommand aCreatePromptCommand() {
        return CreatePromptCommand.builder().name("Test Prompt").slug(slug()).provider(LLMProvider.OPEN_AI)
                .model("gpt-3.5-turbo").systemMessage("You are a helpful assistant.")
                .userMessageTemplate("Hello, {name}!").inputSchema(payloadSchema()).outputSchema(payloadSchema())
                .modelOptions(new HashMap<>()).outputType(PromptOutputType.TEXT).build();
    }

    public static PromptCreationResponse aPromptCreationResponse() {
        return PromptCreationResponse.builder().id(PromptId.random()).slug(slug()).build();
    }

    public static CreatePromptInput aCreatePromptInput() {
        return CreatePromptInput.builder().name("Test Prompt").slug(slug().getValue())
                .provider(LLMProvider.OPEN_AI.name()).model("gpt-3.5-turbo")
                .systemMessage("You are a helpful assistant.").userMessageTemplate("Hello, {name}!")
                .inputSchema(JSON_SCHEMA_NODE).outputSchema(JSON_SCHEMA_NODE).modelOptions(new HashMap<>())
                .outputType(PromptOutputType.TEXT.name()).build();
    }

    public static PromptCreationOutput aPromptCreationOutput() {
        return PromptCreationOutput.builder().id(UUID.randomUUID()).slug(slug().getValue()).build();
    }

}

package com.github.muhsenerdev.genai.support.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.github.muhsenerdev.genai.application.prompt.CreatePromptDefCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptDefCreationResponse;
import com.github.muhsenerdev.genai.domain.prompt.LLMProvider;
import com.github.muhsenerdev.genai.domain.prompt.PromptCreationDetails;
import com.github.muhsenerdev.genai.domain.prompt.PromptId;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;
import com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt.CreatePromptRequest;
import com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt.PromptCreationRestResponse;

public class PromptTestData {

    public static CreatePromptRequest.CreatePromptRequestBuilder aCreatePromptRequest() {
        return CreatePromptRequest.builder().name("test-prompt-" + UUID.randomUUID().toString().substring(0, 8))
                .slug(TestData.slug().getValue()).provider(LLMProvider.OPEN_AI.name()).model("test-model")
                .systemMessage("This is a test system message.")
                .userMessageTemplate("User message template with {variable}.")
                .inputSchema(TestData.payloadBlueprint().getRawSchemaContent())
                .outputSchema(TestData.payloadBlueprint().getRawSchemaContent())
                .modelOptions(createRandomModelOptions());
    }

    public static PromptCreationRestResponse.PromptCreationRestResponseBuilder aPromptCreationRestResponse() {
        return PromptCreationRestResponse.builder().id(UUID.randomUUID()).slug(TestData.slug().getValue());
    }

    private static Map<String, Object> createRandomModelOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("temperature", ThreadLocalRandom.current().nextDouble(0.1, 1.0));
        options.put("maxTokens", ThreadLocalRandom.current().nextInt(100, 1000));
        options.put("topP", ThreadLocalRandom.current().nextDouble(0.5, 1.0));
        return options;
    }

    /**
     * Creates a test instance of PromptDefCreationResponse with random values.
     *
     * @return a new instance of PromptDefCreationResponse
     */
    public static PromptDefCreationResponse.PromptDefCreationResponseBuilder aPromptDefCreationResponse() {
        return PromptDefCreationResponse.builder().id(PromptId.random()).slug(TestData.slug());
    }

    /**
     * Creates a test instance of CreatePromptDefCommand with random values. Note:
     * inputSchema and outputSchema need to be provided as they are user-defined
     * types.
     *
     * @return a builder for CreatePromptDefCommand with random values
     */
    public static CreatePromptDefCommand.CreatePromptDefCommandBuilder aCreatePromptDefCommand() {
        return CreatePromptDefCommand.builder().name("Test Prompt " + UUID.randomUUID().toString().substring(0, 8))
                .provider(LLMProvider.OPEN_AI).slug(TestData.slug()).model("gpt-4")
                .systemMessage("You are a helpful assistant.").userMessageTemplate("Hello, {name}!")
                .modelOptions(Map.of("temperature", 0.7, "max_tokens", 1000, "top_p", 1.0))
                .inputSchema(TestData.payloadBlueprint()).outputSchema(TestData.payloadBlueprint())
                .outputType(PromptOutputType.TEXT)

        ;
    }

    /**
     * Creates a test instance of PromptCreationDetails with random values. Note:
     * inputSchema, outputSchema, and slug need to be provided as they are
     * user-defined types.
     *
     * @return a builder for PromptCreationDetails with random values
     */
    public static PromptCreationDetails.PromptCreationDetailsBuilder aPromptCreationDetails() {
        return PromptCreationDetails.builder().name("Test Prompt " + UUID.randomUUID().toString().substring(0, 8))
                .provider(LLMProvider.values()[(int) (Math.random() * LLMProvider.values().length)])
                .model("gpt-" + (3 + (int) (Math.random() * 2)) + ".5-turbo")
                .systemMessage("You are a helpful assistant. " + UUID.randomUUID())
                .userMessageTemplate("Hello, {name}! " + UUID.randomUUID())
                .modelOptions(Map.of("temperature", 0.1 + (Math.random() * 1.9), "max_tokens",
                        100 + (int) (Math.random() * 900), "top_p", 0.1 + (Math.random() * 0.9)))
                .slug(TestData.slug()).inputSchema(TestData.payloadBlueprint())
                .outputSchema(TestData.payloadBlueprint()).outputType(PromptOutputType.TEXT);
    }

}

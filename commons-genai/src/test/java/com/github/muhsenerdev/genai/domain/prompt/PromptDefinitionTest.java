package com.github.muhsenerdev.genai.domain.prompt;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.support.data.PromptTestData;
import com.github.muhsenerdev.genai.support.data.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromptDefinitionTest {

    private String name;
    private Slug slug;
    private LLMProvider provider;
    private String model;
    private String systemMessage;
    private String userMessageTemplate;
    private PayloadSchema inputSchema;
    private PayloadSchema outputSchema;
    private Map<String, Object> modelOptions;
    private PromptOutputType outputType;

    @BeforeEach
    void setUp() {
        name = "Test Prompt";
        slug = TestData.slug();
        provider = LLMProvider.OPEN_AI;
        model = "gpt-4";
        systemMessage = "System Message";
        userMessageTemplate = "User Message";
        inputSchema = PromptTestData.payloadSchema();
        outputSchema = PromptTestData.payloadSchema();
        modelOptions = Collections.singletonMap("temp", 0.7);
        outputType = PromptOutputType.TEXT;
    }

    @Test
    @DisplayName("should create prompt definition")
    void should_create_prompt_definition() {
        PromptDefinition definition = PromptDefinition.builder().name(name).slug(slug).provider(provider).model(model)
                .systemMessage(systemMessage).userMessageTemplate(userMessageTemplate).inputSchema(inputSchema)
                .outputSchema(outputSchema).modelOptions(modelOptions).outputType(outputType).build();

        assertThat(definition).isNotNull();
        assertThat(definition.getId()).isNotNull();
        assertThat(definition.getName()).isEqualTo(name);
        assertThat(definition.getSlug()).isEqualTo(slug);
        assertThat(definition.getProvider()).isEqualTo(provider);
        assertThat(definition.getModel()).isEqualTo(model);
        assertThat(definition.getSystemMessage()).isEqualTo(systemMessage);
        assertThat(definition.getUserMessageTemplate()).isEqualTo(userMessageTemplate);
        assertThat(definition.getInputSchema()).isEqualTo(inputSchema);
        assertThat(definition.getOutputSchema()).isEqualTo(outputSchema);
        assertThat(definition.getModelOptions()).isEqualTo(modelOptions);
    }

    @Test
    @DisplayName("should fail when slug is null")
    void should_fail_when_slug_is_null() {
        var builder = PromptDefinition.builder().name(name).provider(provider).model(model)
                .userMessageTemplate(userMessageTemplate).outputType(outputType);

        assertThatThrownBy(builder::build).isInstanceOf(InvalidDomainObjectException.class)
                .hasMessage("Prompt slug is required.");
    }

    @Test
    @DisplayName("should fail when provider is null")
    void should_fail_when_provider_is_null() {
        var builder = PromptDefinition.builder().name(name).slug(slug).model(model)
                .userMessageTemplate(userMessageTemplate).outputType(outputType);

        assertThatThrownBy(builder::build).isInstanceOf(InvalidDomainObjectException.class)
                .hasMessage("prompt.provider.required");
    }

    @Test
    @DisplayName("should fail when model is null")
    void should_fail_when_model_is_null() {
        var builder = PromptDefinition.builder().name(name).slug(slug).provider(provider)
                .userMessageTemplate(userMessageTemplate).outputType(outputType);

        assertThatThrownBy(builder::build).isInstanceOf(InvalidDomainObjectException.class)
                .hasMessage("prompt.model.required");
    }

    @Test
    @DisplayName("should fail when user message template is null")
    void should_fail_when_user_message_template_is_null() {
        var builder = PromptDefinition.builder().name(name).slug(slug).provider(provider).model(model)
                .outputType(outputType);

        assertThatThrownBy(builder::build).isInstanceOf(InvalidDomainObjectException.class)
                .hasMessage("prompt.user-message-template.required");
    }

    @Test
    @DisplayName("should fail when output type is null")
    void should_fail_when_output_type_is_null() {
        var builder = PromptDefinition.builder().name(name).slug(slug).provider(provider).model(model)
                .userMessageTemplate(userMessageTemplate);

        assertThatThrownBy(builder::build).isInstanceOf(InvalidDomainObjectException.class)
                .hasMessage("prompt.output-type.required");
    }
}
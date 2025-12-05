package com.github.muhsenerdev.genai.domain.prompt;

import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.genai.support.config.TestBeans;
import com.github.muhsenerdev.genai.support.data.PromptTestData;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class PromptFactoryTest {

    PromptFactory factory = TestBeans.promptFactory();

    PromptCreationData data = PromptTestData.aPromptCreationData();

    @Test
    @DisplayName("Create Prompt Definition - Success")
    void create_should_create_prompt_definition() {
        PromptDefinition definition = factory.create(data);

        assertNotNull(definition.getId());
        assertEquals(data.name(), definition.getName());
        assertEquals(data.slug(), definition.getSlug());
        assertEquals(data.provider(), definition.getProvider());
        assertEquals(data.model(), definition.getModel());
        assertEquals(data.systemMessage(), definition.getSystemMessage());
        assertEquals(data.userMessageTemplate(), definition.getUserMessageTemplate());
        assertEquals(data.modelOptions(), definition.getModelOptions());
        assertEquals(data.inputSchema(), definition.getInputSchema());
        assertEquals(data.outputSchema(), definition.getOutputSchema());
        assertEquals(data.outputType(), definition.getOutputType());

        assertNull(definition.getCreatedAt());
        assertNull(definition.getUpdatedAt());
        assertNull(definition.getCreatedBy());
        assertNull(definition.getUpdatedBy());
        assertFalse(definition.isDeleted());
        assertNull(definition.getDeletedAt());

    }
}
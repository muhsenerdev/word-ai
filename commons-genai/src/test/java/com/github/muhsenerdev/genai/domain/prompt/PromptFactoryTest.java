package com.github.muhsenerdev.genai.domain.prompt;

import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.genai.support.config.TestBeans;
import com.github.muhsenerdev.genai.support.data.PromptTestData;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class PromptFactoryTest {

    PromptFactory factory = TestBeans.promptFactory();

    PromptCreationDetails details = PromptTestData.aPromptCreationDetails().build();

    @Test
    @DisplayName("Create Prompt Definition - Success")
    void create_should_create_prompt_definition() {
        PromptDefinition definition = factory.create(details);

        assertEquals(details.name(), definition.getName());
        assertEquals(details.slug(), definition.getSlug());
        assertEquals(details.provider(), definition.getProvider());
        assertEquals(details.model(), definition.getModel());
        assertEquals(details.systemMessage(), definition.getSystemMessage());
        assertEquals(details.userMessageTemplate(), definition.getUserMessageTemplate());
        assertEquals(details.modelOptions(), definition.getModelOptions());
        assertEquals(details.inputSchema(), definition.getInputSchema());
        assertEquals(details.outputSchema(), definition.getOutputSchema());
        assertNotNull(definition.getId());
        assertNull(definition.getCreatedAt());
        assertNull(definition.getUpdatedAt());
        assertNull(definition.getCreatedBy());
        assertNull(definition.getUpdatedBy());
        assertFalse(definition.isDeleted());
        assertNull(definition.getDeletedAt());
        assertEquals(details.outputType(), definition.getOutputType());

    }
}
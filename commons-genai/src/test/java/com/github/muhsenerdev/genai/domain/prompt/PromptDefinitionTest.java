package com.github.muhsenerdev.genai.domain.prompt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PromptDefinitionTest {

    @Test
    void equals_should_return_true() {
        PromptDefinition aDef = getADef();

        PromptDefinition anotherDef = PromptDefinitionTestBuilder.from(aDef).build();

        assertEquals(aDef, anotherDef);

    }

    private static PromptDefinition getADef() {
        return PromptDefinitionTestBuilder.aPromptDefinition().build();
    }

    @Test
    void equals_whenNameIsDifferent_shouldReturnFalse() {
        PromptDefinition aDef = getADef();

        PromptDefinition anotherDef = PromptDefinitionTestBuilder.from(aDef).withName("diff").build();

        assertNotEquals(aDef, anotherDef);
    }
}
package com.github.muhsenerdev.genai.domain.prompt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.core.Assert;

@Component
@RequiredArgsConstructor
public class PromptFactory {

    public PromptDefinition create(PromptCreationData data) throws IllegalArgumentException {
        Assert.notNull(data, "PromptCreationData cannot be null to create prompt.");
        return PromptDefinition.builder().name(data.name()).slug(data.slug()).provider(data.provider())
                .systemMessage(data.systemMessage()).model(data.model()).userMessageTemplate(data.userMessageTemplate())
                .inputSchema(data.inputSchema()).outputSchema(data.outputSchema()).modelOptions(data.modelOptions())
                .outputType(data.outputType()).build();
    }
}

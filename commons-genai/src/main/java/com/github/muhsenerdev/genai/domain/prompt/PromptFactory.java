package com.github.muhsenerdev.genai.domain.prompt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromptFactory {

    public PromptDefinition create(PromptCreationDetails details) {
        return PromptDefinition.builder().name(details.name()).slug(details.slug()).provider(details.provider())
                .systemMessage(details.systemMessage()).model(details.model())
                .userMessageTemplate(details.userMessageTemplate()).inputSchema(details.inputSchema())
                .outputSchema(details.outputSchema()).modelOptions(details.modelOptions())
                .outputType(details.outputType()).build();
    }
}

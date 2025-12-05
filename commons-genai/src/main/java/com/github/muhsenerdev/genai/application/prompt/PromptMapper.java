package com.github.muhsenerdev.genai.application.prompt;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.genai.domain.prompt.PromptCreationData;

@Component
public class PromptMapper {

    public PromptCreationData toCreationData(CreatePromptCommand command) {
        if (command == null)
            return null;

        return PromptCreationData.builder().name(command.name()).inputSchema(command.inputSchema())
                .outputSchema(command.outputSchema()).model(command.model()).modelOptions(command.modelOptions())
                .provider(command.provider()).slug(command.slug()).outputType(command.outputType())
                .userMessageTemplate(command.userMessageTemplate()).systemMessage(command.systemMessage()).build();
    }
}

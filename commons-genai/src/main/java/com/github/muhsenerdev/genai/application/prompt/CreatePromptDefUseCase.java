package com.github.muhsenerdev.genai.application.prompt;

public interface CreatePromptDefUseCase {

    PromptDefCreationResponse handle(CreatePromptDefCommand command);
}

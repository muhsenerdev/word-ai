package com.github.muhsenerdev.genai.application.prompt;

public interface RunPromptUseCase {

    PromptRunResponse handle(RunPromptCommand command);
}

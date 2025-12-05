package com.github.muhsenerdev.genai.application.prompt;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromptApplicationServiceImpl implements PromptApplicationService {

    private final RunPromptUseCase runPromptUseCase;
    private final CreatePromptCommandHandler createPromptCommandHandler;

    @Override
    public PromptRunResponse run(RunPromptCommand command) {
        return runPromptUseCase.handle(command);
    }

    @Override
    public PromptCreationResponse create(CreatePromptCommand command) {
        return createPromptCommandHandler.handle(command);
    }

}

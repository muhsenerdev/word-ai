package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.core.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptApplicationServiceImpl implements PromptApplicationService {

    private final CreatePromptDefUseCase createPromptDefUseCase;
    private final RunPromptUseCase runPromptUseCase;

    @Override
    public PromptDefCreationResponse create(CreatePromptDefCommand command)
            throws BusinessValidationException, DuplicateResourceException, SystemException {
        return createPromptDefUseCase.handle(command);
    }

    @Override
    public PromptRunResponse run(RunPromptCommand command) {
        return runPromptUseCase.handle(command);
    }
}

package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;
import com.github.muhsenerdev.commons.core.exception.SystemException;

public interface PromptApplicationService {

    PromptRunResponse run(RunPromptCommand command)
            throws BusinessValidationException, ResourceNotFoundException, SystemException;

    PromptCreationResponse create(CreatePromptCommand command);

}

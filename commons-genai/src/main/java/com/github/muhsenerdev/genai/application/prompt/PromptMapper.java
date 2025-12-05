package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.genai.domain.prompt.PromptCreationDetails;

public interface PromptMapper {

    PromptCreationDetails toCreationDetails(CreatePromptDefCommand command);
}

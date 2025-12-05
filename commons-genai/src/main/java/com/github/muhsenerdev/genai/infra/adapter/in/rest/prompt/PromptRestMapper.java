package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import com.github.muhsenerdev.genai.application.prompt.CreatePromptDefCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptDefCreationResponse;

// Uses GenVoMapper.
public interface PromptRestMapper {

    CreatePromptDefCommand toCreateCommand(CreatePromptRequest source);

    PromptCreationRestResponse toRestResponse(PromptDefCreationResponse source);

}

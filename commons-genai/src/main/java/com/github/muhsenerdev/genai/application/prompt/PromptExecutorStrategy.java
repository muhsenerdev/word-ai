package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;

import java.util.Map;

public interface PromptExecutorStrategy {

    boolean supports(PromptOutputType outputType);

    AiResponse execute(PromptDefinition definition, Map<String, Object> inputVariables) throws PromptExecutionException;
}

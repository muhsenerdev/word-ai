package com.github.muhsenerdev.genai.application.prompt;

import lombok.Builder;

public record PromptRunResponse(AiResponse output) {

    @Builder(toBuilder = true)
    public PromptRunResponse(AiResponse output) {
        this.output = output;
    }
}

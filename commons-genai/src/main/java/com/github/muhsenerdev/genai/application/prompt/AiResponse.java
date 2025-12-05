package com.github.muhsenerdev.genai.application.prompt;

import lombok.Getter;

@Getter
public abstract class AiResponse {

    private final AiResponseType type;

    public AiResponse(AiResponseType type) {
        this.type = type;
    }

}

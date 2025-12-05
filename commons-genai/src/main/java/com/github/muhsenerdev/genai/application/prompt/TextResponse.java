package com.github.muhsenerdev.genai.application.prompt;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class TextResponse extends AiResponse {

    private final Map<String, Object> outputMap;

    @Builder(toBuilder = true)
    public TextResponse(Map<String, Object> outputMap) {
        super(AiResponseType.TEXT);
        this.outputMap = outputMap;
    }
}

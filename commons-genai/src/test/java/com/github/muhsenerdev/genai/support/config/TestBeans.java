package com.github.muhsenerdev.genai.support.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprintFactory;
import com.github.muhsenerdev.genai.domain.prompt.PayloadValidator;
import com.github.muhsenerdev.genai.domain.prompt.PromptFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestBeans {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final PayloadBlueprintFactory PAYLOAD_BLUEPRINT_FACTORY;
    private static final PromptFactory PROMPT_FACTORY;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        PAYLOAD_BLUEPRINT_FACTORY = new PayloadBlueprintFactory(OBJECT_MAPPER);
        PROMPT_FACTORY = new PromptFactory();
    }

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    public static PayloadBlueprintFactory payloadBlueprintFactory() {
        return PAYLOAD_BLUEPRINT_FACTORY;
    }

    public static PromptFactory promptFactory() {
        return PROMPT_FACTORY;
    }

    public static PayloadValidator payloadValidator() {
        return new PayloadValidator(objectMapper());
    }
}

package com.github.muhsenerdev.genai.support.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.genai.application.prompt.PromptVoMapper;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchemaFactory;
import com.github.muhsenerdev.genai.domain.prompt.PayloadValidator;
import com.github.muhsenerdev.genai.domain.prompt.PromptFactory;
import com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt.PromptWebMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestBeans {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final PromptFactory PROMPT_FACTORY;
    private static final PayloadSchemaFactory SCHEMA_FACTORY;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        PROMPT_FACTORY = new PromptFactory();
        SCHEMA_FACTORY = new PayloadSchemaFactory();
    }

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    public static PayloadSchemaFactory payloadSchemaFactory() {
        return SCHEMA_FACTORY;
    }

    public static PromptFactory promptFactory() {
        return PROMPT_FACTORY;
    }

    public static PayloadValidator payloadValidator() {
        return new PayloadValidator(objectMapper());
    }

    public static PromptVoMapper voMapper() {
        return new PromptVoMapper(SCHEMA_FACTORY);
    }

    public static PromptWebMapper promptWebMapper() {
        return new PromptWebMapper(voMapper());
    }
}

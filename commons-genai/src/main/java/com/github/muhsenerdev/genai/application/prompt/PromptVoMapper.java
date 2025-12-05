package com.github.muhsenerdev.genai.application.prompt;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.muhsenerdev.commons.core.SingleValueObject;
import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.domain.prompt.LLMProvider;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchema;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchemaFactory;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PromptVoMapper {

	private final PayloadSchemaFactory schemaFactory;

	public LLMProvider toLLMProvider(String source) {
		return Optional.ofNullable(source).map(LLMProvider::fromString).orElse(null);
	}

	public Slug toSlug(String source) {
		return Optional.ofNullable(source).map(Slug::of).orElse(null);
	}

	public PayloadSchema toPayloadSchema(JsonNode source) {
		return Optional.ofNullable(source).map(schemaFactory::create).orElse(null);
	}

	public PromptOutputType toOutputType(String source) {
		return Optional.ofNullable(source).map(PromptOutputType::fromString).orElse(null);
	}

	public <T> T fromId(BaseEmbeddableId<T> source) {
		return source == null ? null : source.getValue();
	}

	public <T> T fromSingleVO(SingleValueObject<T> source) {
		return source == null ? null : source.getValue();
	}
}

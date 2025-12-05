package com.github.muhsenerdev.genai.application.prompt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.domain.prompt.LLMProvider;
import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprint;
import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprintFactory;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;

public class GenVoMapper {

	private final PayloadBlueprintFactory factory;

	public GenVoMapper() {
		this.factory = new PayloadBlueprintFactory(new ObjectMapper());
	}

	public Slug toSlug(String value) {
		return value == null ? null : Slug.of(value);
	}

	public String fromSlug(Slug from) {
		return from == null ? null : from.getValue();
	}

	public LLMProvider toProvider(String value) {
		return value == null ? null : LLMProvider.of(value);
	}

	public PayloadBlueprint toPayloadBluePrint(String value) {
		return value == null ? null : factory.create(value);
	}

	public PromptOutputType toOutputType(String value) {
		return PromptOutputType.of(value);
	}
}

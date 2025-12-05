package com.github.muhsenerdev.wordai.words.infra.config;

import io.swagger.v3.oas.models.examples.Example;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordsOpenApiConfiguration {

	public static final String EXAMPLE_INVALID_REQUEST = "InvalidRequest";
	public static final String EXAMPLE_INVALID_LANGUAGE = "InvalidLanguage";
	public static final String EXAMPLE_UNKNOWN_CEFR_LEVEL = "UnknownCefrLevel";
	public static final String EXAMPLE_INVALID_PART_OF_SPEECH = "InvalidPartOfSpeech";
	public static final String EXAMPLE_WORDS_REQUIRED = "WordsRequired";

	@Bean
	public OpenApiCustomizer wordsOpenApiCustomizer() {
		return openApi -> {
			if (openApi.getComponents() == null) {
				openApi.setComponents(new io.swagger.v3.oas.models.Components());
			}

			openApi.getComponents().addExamples(EXAMPLE_INVALID_REQUEST,
					new Example().summary("Invalid request (Validation errors)").value("""
							{
							    "status": 400,
							    "path": "/api/v1/words/bulk",
							    "timestamp": "2025-12-04T22:14:03.974060Z",
							    "errors": {
							        "word.cefr_level.required": "word.cefr_level.required",
							        "word.language.required": "word.language.required",
							        "word.text.required": "word.text.required",
							        "word.part_of_speech.required": "word.part_of_speech.required"
							    },
							    "message": "Invalid request."
							}
							"""));

			openApi.getComponents().addExamples(EXAMPLE_INVALID_LANGUAGE, new Example().summary("Invalid language").value("""
					{
					    "status": 400,
					    "path": "/api/v1/words/bulk",
					    "timestamp": "2025-12-04T22:16:32.856360Z",
					    "errors": {
					        "language.unknown": "Language is not supported."
					    },
					    "message": "Language is not supported."
					}
					"""));

			openApi.getComponents().addExamples(EXAMPLE_UNKNOWN_CEFR_LEVEL,
					new Example().summary("Unknown CEFR level").value("""
							{
							    "status": 400,
							    "path": "/api/v1/words/bulk",
							    "timestamp": "2025-12-04T22:17:13.059327Z",
							    "errors": {
							        "cefr-level.unknown": "CEFR level is not supported."
							    },
							    "message": "CEFR level is not supported."
							}
							"""));

			openApi.getComponents().addExamples(EXAMPLE_INVALID_PART_OF_SPEECH,
					new Example().summary("Invalid PartOfSpeech value").value("""
							{
							    "status": 400,
							    "path": "/api/v1/words/bulk",
							    "timestamp": "2025-12-04T22:17:30.930526Z",
							    "errors": {
							        "part-of-speech.unknown": "Part of speech is not supported."
							    },
							    "message": "Part of speech is not supported."
							}
							"""));

			openApi.getComponents().addExamples(EXAMPLE_WORDS_REQUIRED, new Example().summary("Words required").value("""
					{
					    "status": 400,
					    "path": "/api/v1/words/bulk",
					    "timestamp": "2025-12-05T00:04:04.872434Z",
					    "errors": {
					        "words.required": "words.required"
					    },
					    "message": "Invalid request."
					}
					"""));
		};
	}
}

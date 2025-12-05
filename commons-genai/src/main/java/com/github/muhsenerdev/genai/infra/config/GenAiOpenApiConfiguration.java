package com.github.muhsenerdev.genai.infra.config;

import io.swagger.v3.oas.models.examples.Example;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenAiOpenApiConfiguration {

	public static final String EXAMPLE_PROMPT_VALIDATION_ERRORS = "PromptValidationErrors";
	public static final String EXAMPLE_PROMPT_SCHEMA_ERROR = "PromptSchemaError";
	public static final String EXAMPLE_PROMPT_SLUG_CONFLICT = "PromptSlugConflict";

	@Bean
	public OpenApiCustomizer genAiOpenApiCustomizer() {
		return openApi -> {
			if (openApi.getComponents() == null) {
				openApi.setComponents(new io.swagger.v3.oas.models.Components());
			}

			openApi.getComponents().addExamples(EXAMPLE_PROMPT_VALIDATION_ERRORS, new Example().summary("Validation Errors")
					.description("Standard validation errors for missing or invalid fields.").value("""
							{
							    "status": 400,
							    "path": "/api/v1/prompts",
							    "timestamp": "2025-12-05T15:28:16.024608Z",
							    "errors": {
							        "prompt.name.required": "prompt.name.required",
							        "prompt.model.required": "prompt.model.required",
							        "prompt.output_type.required": "prompt.output_type.required",
							        "prompt.user_message_template.required": "prompt.user_message_template.required",
							        "prompt.provider.required": "prompt.provider.required",
							        "prompt.slug.required": "prompt.slug.required"
							    },
							    "message": "Invalid request."
							}
							"""));

			openApi.getComponents().addExamples(EXAMPLE_PROMPT_SCHEMA_ERROR, new Example().summary("Invalid Schema Error")
					.description("Error when the provided JSON schema is invalid.").value(
							"""
									{
									    "status": 400,
									    "path": "/api/v1/prompts",
									    "timestamp": "2025-12-05T15:33:43.865093Z",
									    "errors": {
									        "payload-schema.invalid": "Failed to parse schema: Invalid JSON Schema rules: [$.properties.text.type: does not have a value in the enumeration [\\"array\\", \\"boolean\\", \\"integer\\", \\"null\\", \\"number\\", \\"object\\", \\"string\\"], $.properties.text.type: string found, array expected]"
									    },
									    "message": "Failed to parse schema: Invalid JSON Schema rules: [$.properties.text.type: does not have a value in the enumeration [\\"array\\", \\"boolean\\", \\"integer\\", \\"null\\", \\"number\\", \\"object\\", \\"string\\"], $.properties.text.type: string found, array expected]"
									}
									"""));

			openApi.getComponents().addExamples(EXAMPLE_PROMPT_SLUG_CONFLICT,
					new Example().summary("Duplicate Slug Conflict").description("The prompt slug is already in use.").value("""
							{
							    "status": 409,
							    "path": "/api/v1/prompts",
							    "timestamp": "2025-12-05T15:44:03.633759Z",
							    "errors": {
							        "prompt.duplicated.slug": "prompt with slug: 'summarize-text' already exists"
							    },
							    "message": "prompt with slug: 'summarize-text' already exists",
							    "resource": "prompt",
							    "field": "slug",
							    "value": "summarize-text"
							}
							"""));
		};
	}
}

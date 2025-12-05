package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Data
@Schema(description = "Response payload after creating a prompt")
public class PromptCreationOutput {

	@Schema(description = "The unique identifier of the created prompt")
	private UUID id;

	@Schema(description = "The unique slug of the created prompt", example = "summarize-text")
	private String slug;
}
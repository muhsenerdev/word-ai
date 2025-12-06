package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.session;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
@Schema(description = "Response containing the started session information")
public record StartSessionOutput(
		@Schema(description = "Unique identifier of the created session", example = "123e4567-e89b-12d3-a456-426614174000", required = true) UUID sessionId) {}

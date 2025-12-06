package com.github.muhsenerdev.commons.core.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "Base error response")
@AllArgsConstructor
public abstract class ErrorResponse {

	@Schema(description = "HTTP status code")
	private int status;

	@Schema(description = "Request path")
	private String path;

	@Schema(description = "Timestamp of the error")
	private Instant timestamp;

	@Schema(description = "Map of validation errors or additional details (key -> message)")
	private java.util.Map<String, String> errors;

	@Schema(description = "Error message")
	private String message;

}

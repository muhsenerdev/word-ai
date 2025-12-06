package com.github.muhsenerdev.commons.core.response;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Resource not found error response (404)")
public class NotFoundResponse extends ErrorResponse {

	@JsonProperty("resource_name")
	@Schema(description = "Name of the resource")
	private String resourceName;

	@Schema(description = "Field that caused the error")
	private String field;

	@Schema(description = "Value of the field")
	private String value;

	public static NotFoundResponse of(ResourceNotFoundException ex, String path, String message,
			Map<String, String> errors) {
		return NotFoundResponse.builder().timestamp(Instant.now()).status(HttpStatus.NOT_FOUND.value()).path(path)
				.resourceName(ex.getResource()).field(ex.getField())
				.value(Optional.ofNullable(ex.getValue()).orElse(null).toString()).message(message).errors(errors)
				.build();
	}

}

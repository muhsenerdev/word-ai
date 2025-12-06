package com.github.muhsenerdev.commons.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resource not found error response (404)")
public class NotFoundResponse extends ErrorResponse {

	@JsonProperty("resource_name")
	@Schema(description = "Name of the resource")
	private String resourceName;

	@Schema(description = "Field that caused the error")
	private String field;

	@Schema(description = "Value of the field")
	private String value;
}

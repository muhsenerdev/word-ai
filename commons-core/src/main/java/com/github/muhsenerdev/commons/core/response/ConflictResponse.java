package com.github.muhsenerdev.commons.core.response;

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
@Schema(description = "Conflict error response (409)")
public class ConflictResponse extends ErrorResponse {

    @Schema(description = "Name of the resource")
    private String resource;

    @Schema(description = "Field that caused the conflict")
    private String field;

    @Schema(description = "Value that caused the conflict")
    private String value;
}

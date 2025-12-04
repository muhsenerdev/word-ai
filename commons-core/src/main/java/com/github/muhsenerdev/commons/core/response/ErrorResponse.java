package com.github.muhsenerdev.commons.core.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Base error response")
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

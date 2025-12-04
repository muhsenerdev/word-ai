package com.github.muhsenerdev.commons.core.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Schema(description = "Bad request error response (400)")
public class BadRequestResponse extends ErrorResponse {

    @Schema(description = "Error message")
    private String message;
}

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
@Schema(description = "Unauthorized error response (401)")
public class UnauthorizedResponse extends ErrorResponse {

    @Schema(description = "Error message")
    private String message;
}

package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.onboarding;

import com.github.muhsenerdev.commons.core.response.ConflictResponse;
import com.github.muhsenerdev.commons.core.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Onboard a new user", description = """
        <h3>Public API</h3>
        <p>This endpoint is accessible to everyone.</p>
        <p>Registers a new user and creates a learner profile.</p>
        <hr>
        <h3>Genel API</h3>
        <p>Bu uç nokta herkese açıktır.</p>
        <p>Yeni bir kullanıcı kaydeder ve bir öğrenci profili oluşturur.</p>
        """)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully onboarded", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OnboardingOutput.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Invalid input example", value = """
                {
                    "status": 400,
                    "path": "/api/v1/auth/register",
                    "timestamp": "2025-12-04T17:11:18.226792Z",
                    "errors": {
                        "password": "Password is required.",
                        "targetLanguage": "Target language is not supported.",
                        "motherLanguage": "Mother language is not supported.",
                        "username": "Username must be between 3 and 50 characters long."
                    },
                    "message": null
                }"""))),
        @ApiResponse(responseCode = "409", description = "Conflict - If username is taken already.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class), examples = @ExampleObject(name = "Username conflict", ref = "#/components/examples/UsernameConflict"))) })
public @interface OnboardingDocs {
}

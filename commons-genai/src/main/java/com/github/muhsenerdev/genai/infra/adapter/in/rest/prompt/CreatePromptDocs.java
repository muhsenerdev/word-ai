package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import com.github.muhsenerdev.commons.core.response.ConflictResponse;
import com.github.muhsenerdev.commons.core.response.ErrorResponse;
import com.github.muhsenerdev.commons.core.response.ForbiddenResponse;
import com.github.muhsenerdev.commons.core.response.InternalResponse;
import com.github.muhsenerdev.commons.core.response.UnauthorizedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Create a new prompt", description = """
		<h3>Private API</h3>
		<p>Creates a new prompt configuration.</p>
		<p>Only users with <b>ROLE_ADMIN</b> can perform this operation.</p>
		<hr>
		<h3>Private API</h3>
		<p>Yeni bir prompt konfigürasyonu oluşturur.</p>
		<p>Bu işlemi sadece <b>ROLE_ADMIN</b> yetkisine sahip kullanıcılar gerçekleştirebilir.</p>
		""", security = @SecurityRequirement(name = "bearerAuth"))
@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Prompt successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PromptCreationOutput.class))),
		@ApiResponse(responseCode = "400", description = "Invalid input or schema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = {
				@ExampleObject(name = "Validation Errors", ref = "#/components/examples/PromptValidationErrors"),
				@ExampleObject(name = "Schema Error", ref = "#/components/examples/PromptSchemaError") })),
		@ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token", content = @Content(schema = @Schema(implementation = UnauthorizedResponse.class), mediaType = "application/json", examples = @ExampleObject(name = "Unauthorized", ref = "#/components/examples/Unauthorized"))),
		@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions", content = @Content(schema = @Schema(implementation = ForbiddenResponse.class), mediaType = "application/json", examples = @ExampleObject(name = "Forbidden", ref = "#/components/examples/Forbidden"))),
		@ApiResponse(responseCode = "409", description = "Conflict - Slug already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictResponse.class), examples = @ExampleObject(name = "Slug Conflict", ref = "#/components/examples/PromptSlugConflict"))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalResponse.class), examples = @ExampleObject(name = "Internal Server Error", ref = "#/components/examples/InternalServerError"))) })
public @interface CreatePromptDocs {
}

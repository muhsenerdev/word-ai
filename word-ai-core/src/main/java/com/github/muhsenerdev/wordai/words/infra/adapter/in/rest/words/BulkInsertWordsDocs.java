package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

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
@Operation(summary = "Bulk insert words", description = """
		<h3>Protected API</h3>
		<p>This endpoint requires <code>ROLE_ADMIN</code> authority.</p>
		<p>Inserts multiple words into the system in bulk.</p>
		<hr>
		<h3>Korumalı API</h3>
		<p>Bu uç nokta <code>ROLE_ADMIN</code> yetkisi gerektirir.</p>
		<p>Sisteme toplu olarak birden fazla kelime ekler.</p>
		""", security = @SecurityRequirement(name = "bearerAuth"))
@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Words successfully inserted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BulkWordInsertionOutput.class))),
		@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = {
				@ExampleObject(name = "Invalid request", ref = "#/components/examples/InvalidRequest"),
				@ExampleObject(name = "Invalid language", ref = "#/components/examples/InvalidLanguage"),
				@ExampleObject(name = "Unknown CEFR level", ref = "#/components/examples/UnknownCefrLevel"),
				@ExampleObject(name = "Invalid PartOfSpeech", ref = "#/components/examples/InvalidPartOfSpeech"),
				@ExampleObject(name = "Words required", ref = "#/components/examples/WordsRequired") })),
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedResponse.class), examples = @ExampleObject(name = "Unauthorized", ref = "#/components/examples/Unauthorized"))),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForbiddenResponse.class), examples = @ExampleObject(name = "Forbidden", ref = "#/components/examples/Forbidden"))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalResponse.class), examples = @ExampleObject(name = "Internal Server Error", ref = "#/components/examples/InternalServerError"))) })
public @interface BulkInsertWordsDocs {
}

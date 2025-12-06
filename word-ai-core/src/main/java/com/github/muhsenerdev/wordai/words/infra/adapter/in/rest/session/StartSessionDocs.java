package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.session;

import static com.github.muhsenerdev.wordai.words.infra.config.WordsOpenApiConfiguration.EXAMPLE_DAILY_LIMIT_REACHED;
import static com.github.muhsenerdev.wordai.words.infra.config.WordsOpenApiConfiguration.EXAMPLE_LEARNER_NOT_FOUND;
import static com.github.muhsenerdev.wordai.words.infra.config.WordsOpenApiConfiguration.EXAMPLE_ALREADY_ACTIVE_SESSION;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.muhsenerdev.commons.core.response.BadRequestResponse;
import com.github.muhsenerdev.commons.core.response.InternalResponse;
import com.github.muhsenerdev.commons.core.response.NotFoundResponse;
import com.github.muhsenerdev.commons.core.response.UnauthorizedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Start a new learning session", description = """
		<h3>Authenticated API</h3>
		<p>This endpoint requires authentication.</p>
		<p>Creates and starts a new learning session for the authenticated user with suggested words.</p>
		<p>User information (userId, mother language, learning language) is automatically extracted from the security principal.</p>
		<hr>
		<h3>Kimlik Doğrulamalı API</h3>
		<p>Bu uç nokta kimlik doğrulaması gerektirir.</p>
		<p>Kimliği doğrulanmış kullanıcı için önerilen kelimelerle yeni bir öğrenme oturumu oluşturur ve başlatır.</p>
		<p>Kullanıcı bilgileri (kullanıcı kimliği, ana dil, öğrenme dili) otomatik olarak güvenlik ana nesnesinden alınır.</p>
		""")
@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Session started successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StartSessionOutput.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request - Daily session limit reached", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class), examples = {
				@ExampleObject(name = "Daily limit reached", ref = "#/components/examples/"
						+ EXAMPLE_DAILY_LIMIT_REACHED),
				@ExampleObject(name = "Already active session", ref = "#/components/examples/"
						+ EXAMPLE_ALREADY_ACTIVE_SESSION) })),
		@ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedResponse.class), examples = @ExampleObject(name = "Unauthorized", ref = "#/components/examples/Unauthorized"))),
		@ApiResponse(responseCode = "404", description = "Not Found - Learner not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundResponse.class), examples = @ExampleObject(name = "Learner not found", ref = "#/components/examples/"
				+ EXAMPLE_LEARNER_NOT_FOUND))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalResponse.class), examples = @ExampleObject(name = "Internal server error", ref = "#/components/examples/InternalServerError"))) })
@SecurityRequirement(name = "bearerAuth")
public @interface StartSessionDocs {
}

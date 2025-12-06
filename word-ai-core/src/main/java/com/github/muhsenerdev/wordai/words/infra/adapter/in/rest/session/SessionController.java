package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.session;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.muhsenerdev.wordai.words.application.session.SessionStartResponse;
import com.github.muhsenerdev.wordai.words.application.session.StartSessionCommand;
import com.github.muhsenerdev.wordai.words.application.session.StartSessionCommandHandler;
import com.github.muhsenerdev.wordai.words.infra.config.SecurityPrincipal;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
@Tag(name = "Sessions", description = "Learning session management endpoints")
public class SessionController {

	private final StartSessionCommandHandler startSessionCommandHandler;
	private final SessionWebMapper mapper;

	@PostMapping("/start")
	@StartSessionDocs
	public ResponseEntity<StartSessionOutput> startSession(@AuthenticationPrincipal SecurityPrincipal principal) {
		StartSessionCommand command = mapper.toStartSessionCommand(principal);
		SessionStartResponse response = startSessionCommandHandler.handle(command);
		StartSessionOutput output = mapper.toStartSessionOutput(response);
		return ResponseEntity.ok(output);
	}
}

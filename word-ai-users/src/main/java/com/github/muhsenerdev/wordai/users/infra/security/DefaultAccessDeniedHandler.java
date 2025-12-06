package com.github.muhsenerdev.wordai.users.infra.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.commons.core.response.ForbiddenResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		log.debug("Access denied: {}", accessDeniedException.getMessage());

		ForbiddenResponse forbiddenResponse = ForbiddenResponse.builder().status(HttpStatus.FORBIDDEN.value())
				.path(request.getRequestURI()).timestamp(Instant.now())
				.message("Access denied: " + accessDeniedException.getMessage()).build();

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(forbiddenResponse));
		response.flushBuffer();
	}
}

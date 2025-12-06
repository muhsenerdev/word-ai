package com.github.muhsenerdev.commons.core.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.muhsenerdev.commons.core.response.BadRequestResponse;
import com.github.muhsenerdev.commons.core.response.ConflictResponse;
import com.github.muhsenerdev.commons.core.response.ErrorResponse;
import com.github.muhsenerdev.commons.core.response.InternalResponse;
import com.github.muhsenerdev.commons.core.response.NotFoundResponse;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Component
@Slf4j
@SuppressWarnings("null")
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BadRequestResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		logDebug(ex, request);
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String messageKey = error.getDefaultMessage();
			String message = messageSource.getMessage(messageKey, new Object[] {}, messageKey,
					LocaleContextHolder.getLocale());
			errors.put(messageKey, message);
		}

		BadRequestResponse response = BadRequestResponse.builder().timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value()).path(request.getRequestURI()).errors(errors)
				.message("Invalid request.").build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(BusinessValidationException.class)
	@Hidden
	public ResponseEntity<BadRequestResponse> handleBusinessValidationException(BusinessValidationException ex,
			HttpServletRequest request) {
		logDebug(ex, request);
		Map<String, String> errors = new HashMap<>();

		String message = messageSource.getMessage(ex.getCode(), new Object[] {}, ex.getMessage(),
				LocaleContextHolder.getLocale());

		errors.put(ex.getCode(), message);

		BadRequestResponse response = BadRequestResponse.builder().timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value()).path(request.getRequestURI()).errors(errors).message(message)
				.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// For Duplicate
	@ExceptionHandler(DuplicateResourceException.class)
	@Hidden
	public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex,
			HttpServletRequest request) {
		logDebug(ex, request);
		Map<String, String> errors = new HashMap<>();

		String message = messageSource.getMessage(ex.getCode(), new Object[] {}, ex.getMessage(),
				LocaleContextHolder.getLocale());

		errors.put(ex.getCode(), message);

		ConflictResponse response = ConflictResponse.builder().timestamp(Instant.now())
				.status(HttpStatus.CONFLICT.value()).path(request.getRequestURI()).resource(ex.getResource())
				.field(ex.getField()).message(message).value(Optional.ofNullable(ex.getValue()).orElse(null).toString())
				.errors(errors).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	private void logDebug(Exception ex, HttpServletRequest request) {
		log.error("PATH: {}, Reason: {}", request.getRequestURI(), ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@Hidden
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			HttpServletRequest request) {
		logDebug(ex, request);
		Map<String, String> errors = new HashMap<>();

		String message = messageSource.getMessage(ex.getCode(), new Object[] {}, ex.getMessage(),
				LocaleContextHolder.getLocale());

		errors.put(ex.getCode(), message);

		NotFoundResponse response = NotFoundResponse.of(ex, request.getRequestURI(), message, errors);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(Exception.class)
	@Hidden
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
		log.error("PATH: {}, Reason: {}", request.getRequestURI(), ex.getMessage(), ex);

		Map<String, String> errors = new HashMap<>();

		String message = messageSource.getMessage("unexpected.error", new Object[] {}, "unknown",
				LocaleContextHolder.getLocale());

		errors.put("unexpected.error", message);

		InternalResponse response = InternalResponse.builder().timestamp(Instant.now()).message(ex.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).path(request.getRequestURI()).errors(errors).build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}

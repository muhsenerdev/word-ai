package com.github.muhsenerdev.wordai.words.application.words;

import com.github.muhsenerdev.commons.core.exception.ApplicationException;

public class CardGenerationException extends ApplicationException {

	public CardGenerationException(String message) {
		super(message);
	}

	public CardGenerationException(String message, String code) {
		super(message, code);
	}

	public CardGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

}

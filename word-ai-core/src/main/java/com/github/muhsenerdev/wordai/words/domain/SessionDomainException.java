package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.DomainException;

public class SessionDomainException extends DomainException {

	public SessionDomainException(String message) {
		super(message);
	}

	public SessionDomainException(String message, String code) {
		super(message, code);
	}

	public SessionDomainException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionDomainException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

}

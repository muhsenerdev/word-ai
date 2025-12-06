
package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

public enum SessionStatus {
	INACTIVE, ACTIVE, COMPLETED;

	public static SessionStatus fromString(String status) {
		Assert.notNull(status, "Session status cannot be null");
		try {
			return valueOf(status.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidDomainObjectException("Invalid session status: " + status, "session.status.unknown");
		}
	}
}

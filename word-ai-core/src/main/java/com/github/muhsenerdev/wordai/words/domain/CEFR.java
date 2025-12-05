package com.github.muhsenerdev.wordai.words.domain;

import java.util.Locale;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

public enum CEFR {
	A1, A2, B1, B2, C1, C2;

	// static method fromString()
	public static CEFR fromString(String val) {
		Assert.notNull(val, "null CEFR value");
		try {
			return CEFR.valueOf(val.trim().toUpperCase(Locale.UK));
		} catch (Exception e) {
			throw new InvalidDomainObjectException("Unknown CEFR level", "cefr-level.unknown");
		}
	}
}

package com.github.muhsenerdev.commons.jpa;

import java.util.Locale;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.SingleValueObject;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleName extends SingleValueObject<String> {

	public static final String PREFIX = "ROLE_";
	public static final int MIN_LENGTH = 3;
	public static final int MAX_LENGTH = 50;

	@Column
	private String value;

	private RoleName(String value) {
		DomainUtils.hasText(value, "Value of RoleName cannot be null or empty!");
		DomainUtils.checkLength(value, MIN_LENGTH, MAX_LENGTH,
				"RoleName must be between %d and %d characters long!".formatted(MIN_LENGTH, MAX_LENGTH),
				"role-name.length");

		this.value = format(value);
	}

	private String format(String value) {
		assert value != null;

		String upperCaseString = value.trim().toUpperCase(Locale.US);

		if (upperCaseString.startsWith(PREFIX)) {
			return upperCaseString;
		} else {
			return PREFIX + upperCaseString;
		}

	}

	public @NonNull static RoleName of(String value) throws InvalidDomainObjectException {
		return new RoleName(value);
	}

	@Override
	public String getValue() {
		return value;
	}

}

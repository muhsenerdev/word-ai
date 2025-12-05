package com.github.muhsenerdev.commons.core;

import java.time.Instant;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class BaseApplicationEvent {

	@NonNull
	private final Instant timestamp;
	@Nullable
	private final Object source;

}

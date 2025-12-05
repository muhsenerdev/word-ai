package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.DomainUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@lombok.EqualsAndHashCode
public class Mapping {
	private String source;
	private String target;

	protected Mapping(String source, String target) {
		this.source = DomainUtils.hasText(source, "Mapping source cannot be empty", "mapping.source.required");
		this.target = DomainUtils.hasText(target, "Mapping target cannot be empty", "mapping.target.required");
	}

	public static Mapping of(String source, String target) {
		return new Mapping(source, target);
	}
}

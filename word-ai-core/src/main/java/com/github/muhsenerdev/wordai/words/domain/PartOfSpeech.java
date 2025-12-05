package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

public enum PartOfSpeech {
	NOUN, VERB, PHRASAL_VERB, ADJECTIVE, ADVERB, PREPOSITION, CONJUNCTION, PRONOUN, INTERJECTION, DETERMINER, NUMERAL,
	AUXILIARY_VERB, MODAL_VERB, PARTICLE, IDIOM, PHRASE, COLLOCATION, PROVERB, ABBREVIATION, ACRONYM, CONTRACTION, SYMBOL,
	UNKNOWN;

	public static PartOfSpeech of(String value) {
		Assert.notNull(value, "null PartOfSpeech value");
		try {
			return PartOfSpeech.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidDomainObjectException("Invalid PartOfSpeech value: " + value, "part-of-speech.unknown");
		}
	}

	public static PartOfSpeech fromString(String value) {
		return of(value);
	}
}

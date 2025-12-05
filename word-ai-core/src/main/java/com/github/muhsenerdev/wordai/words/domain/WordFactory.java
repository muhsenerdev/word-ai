package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.slugify.Slugify;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WordFactory {

	private final Slugify slugify;

	public Word createWord(String text, PartOfSpeech partOfSpeech, CEFR cefrLevel, Language language)
			throws InvalidDomainObjectException {
		String slug = slugify.slugify(text);
		return Word.builder().text(text).slug(slug).partOfSpeech(partOfSpeech).cefrLevel(cefrLevel).language(language)
				.build();
	}

}

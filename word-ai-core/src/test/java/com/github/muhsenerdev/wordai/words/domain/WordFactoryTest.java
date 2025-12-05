package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.slugify.Slugify;

@ExtendWith(MockitoExtension.class)
class WordFactoryTest {

	@Mock
	private Slugify slugify;

	@InjectMocks
	private WordFactory wordFactory;

	private String text;
	private PartOfSpeech partOfSpeech;
	private CEFR cefrLevel;
	private Language language;

	@BeforeEach
	void setUp() {
		text = "Hello World";
		partOfSpeech = PartOfSpeech.NOUN;
		cefrLevel = CEFR.A1;
		language = Language.ENGLISH;
	}

	@Test
	void shouldCreateWordWithSlug() {
		// Given
		String expectedSlug = "hello-world";
		when(slugify.slugify(text)).thenReturn(expectedSlug);

		// When
		Word word = wordFactory.createWord(text, partOfSpeech, cefrLevel, language);

		// Then
		assertThat(word).isNotNull();
		assertThat(word.getText()).isEqualTo(text);
		assertThat(word.getSlug()).isEqualTo(expectedSlug);
		assertThat(word.getPartOfSpeech()).isEqualTo(partOfSpeech);
		assertThat(word.getCefrLevel()).isEqualTo(cefrLevel);
		assertThat(word.getLanguage()).isEqualTo(language);
	}

	@Test
	void shouldCreateWordWithSlugForComplexText() {
		// Given
		String complexText = "This is a complex text with symbols!@#";
		String expectedSlug = "this-is-a-complex-text-with-symbols";
		when(slugify.slugify(complexText)).thenReturn(expectedSlug);

		// When
		Word word = wordFactory.createWord(complexText, partOfSpeech, cefrLevel, language);

		// Then
		assertThat(word).isNotNull();
		assertThat(word.getText()).isEqualTo(complexText);
		assertThat(word.getSlug()).isEqualTo(expectedSlug);
	}

	// when invalid input, like null text, then throws InvalidDomainException
	@Test
	void shouldThrowInvalidDomainExceptionWhenTextIsNull() {
		// Given
		String text = null;

		// When
		InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class,
				() -> wordFactory.createWord(text, partOfSpeech, cefrLevel, language));

		// Then
		assertThat(exception).isNotNull();
	}
}

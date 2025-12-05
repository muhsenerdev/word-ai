package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WordTest {

	private String text;
	private String slug;
	private PartOfSpeech partOfSpeech;
	private CEFR cefrLevel;
	private Language language;

	@BeforeEach
	void setUp() {
		text = "hello";
		slug = "hello-slug";
		partOfSpeech = PartOfSpeech.NOUN;
		cefrLevel = CEFR.A1;
		language = Language.ENGLISH;
	}

	@Test
	void shouldCreateWord() {
		// When
		Word word = getWord();

		// Then
		assertThat(word).isNotNull();
		assertThat(word.getId()).isNotNull();
		assertThat(word.getText()).isEqualTo(text);
		assertThat(word.getSlug()).isEqualTo(slug);
		assertThat(word.getPartOfSpeech()).isEqualTo(partOfSpeech);
		assertThat(word.getCefrLevel()).isEqualTo(cefrLevel);
		assertThat(word.getLanguage()).isEqualTo(language);
		assertThat(word.isDeleted()).isFalse();
	}

	private Word getWord() {
		return Word.builder().text(text).slug(slug).partOfSpeech(partOfSpeech).cefrLevel(cefrLevel).language(language)
				.build();
	}

	@Test
	void shouldFailToCreateWordWhenTextIsNull() {
		text = null;
		assertThatThrownBy(() -> getWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word text is required.");
	}

	@Test
	void shouldFailToCreateWordWhenSlugIsNull() {
		slug = null;
		assertThatThrownBy(() -> getWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word slug is required.");
	}

	@Test
	void shouldFailToCreateWordWhenPartOfSpeechIsNull() {
		partOfSpeech = null;
		assertThatThrownBy(() -> getWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word partOfSpeech is required.");
	}

	@Test
	void shouldFailToCreateWordWhenCefrLevelIsNull() {
		cefrLevel = null;
		assertThatThrownBy(() -> getWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word cefrLevel is required.");
	}

	@Test
	void shouldFailToCreateWordWhenLanguageIsNull() {
		language = null;
		assertThatThrownBy(() -> getWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word language is required.");
	}
}

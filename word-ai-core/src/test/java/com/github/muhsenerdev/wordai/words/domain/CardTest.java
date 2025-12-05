package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

	private String sourceSentence;
	private String targetSentence;
	private List<Mapping> mappings;
	private Language targetLanguage;
	private WordId wordId;

	@BeforeEach
	void setUp() {
		sourceSentence = "This is a test sentence.";
		targetSentence = "Bu bir test cümlesidir.";
		mappings = Collections.singletonList(Mapping.of("test", "test"));
		targetLanguage = Language.TURKISH;
		wordId = WordId.random();
	}

	@Test
	void shouldCreateCard() {
		// When
		Card card = getCard();

		// Then
		assertThat(card).isNotNull();
		assertThat(card.getId()).isNotNull();
		assertThat(card.getSourceSentence()).isEqualTo(sourceSentence);
		assertThat(card.getTargetSentence()).isEqualTo(targetSentence);
		assertThat(card.getMappings()).isEqualTo(mappings);
		assertThat(card.getTargetLanguage()).isEqualTo(targetLanguage);
		assertThat(card.getWordId()).isEqualTo(wordId);
		assertThat(card.isDeleted()).isFalse();
	}

	private Card getCard() {
		return Card.create(sourceSentence, targetSentence, mappings, targetLanguage, wordId);
	}

	@Test
	void shouldFailToCreateCardWhenSourceSentenceIsNull() {
		sourceSentence = null;
		assertThatThrownBy(() -> getCard()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Source sentence is required.");
	}

	@Test
	void shouldFailToCreateCardWhenTargetSentenceIsNull() {
		targetSentence = null;
		assertThatThrownBy(() -> getCard()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Target sentence is required.");
	}

	@Test
	void shouldFailToCreateCardWhenMappingsIsNull() {
		mappings = null;
		assertThatThrownBy(() -> getCard()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Mappings are required.");
	}

	@Test
	void shouldFailToCreateCardWhenMappingsIsEmpty() {
		mappings = Collections.emptyList();
		assertThatThrownBy(() -> getCard()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Mappings are required.");
	}

	@Test
	void shouldFailToCreateCardWhenTargetLanguageIsNull() {
		targetLanguage = null;
		assertThatThrownBy(() -> getCard()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Target language is required.");
	}

	@Test
	void shouldFailToCreateCardWhenWordIdIsNull() {
		wordId = null;
		assertThatThrownBy(() -> getCard()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word ID is required.");
	}
}

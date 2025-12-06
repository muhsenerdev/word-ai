package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

class SessionWordTest {

	private WordId wordId;
	private SessionId sessionId;

	@BeforeEach
	void setUp() {

		wordId = WordId.random();
		sessionId = SessionId.random();

	}

	@Test
	void shouldCreateSessionWord() {
		// When
		SessionWord sessionWord = getSessionWord();

		// Then
		assertThat(sessionWord).isNotNull();
		assertThat(sessionWord.getId()).isNotNull();
		assertThat(sessionWord.getWordId()).isEqualTo(wordId);
		assertThat(sessionWord.getSessionId()).isEqualTo(sessionId);
		assertThat(sessionWord.isLearned()).isEqualTo(false);
		assertThat(sessionWord.getLearnedAt()).isNull();
	}

	private SessionWord getSessionWord() {
		return SessionWord.of(wordId, sessionId);
	}

	@Test
	void shouldFailToCreateSessionWordWhenWordIdIsNull() {
		wordId = null;
		assertThatThrownBy(() -> getSessionWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Word id cannot be null");
	}

	@Test
	void shouldFailToCreateSessionWordWhenSessionIdIsNull() {
		sessionId = null;
		assertThatThrownBy(() -> getSessionWord()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Session id cannot be null");
	}

	@Test
	void shouldBeEqualWhenTheSame() {
		SessionWord sessionWord1 = SessionWordTestBuilder.aSessionWord().withWordId(WordId.random())
				.withSessionId(SessionId.random()).build();
		SessionWord sessionWord2 = SessionWordTestBuilder.from(sessionWord1).build();

		assertThat(sessionWord1).isEqualTo(sessionWord2);
	}
}

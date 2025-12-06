package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.UserId;

class SessionTest {

	private UserId userId;
	private Set<SessionWord> sessionWords = new HashSet<>();

	@BeforeEach
	void setUp() {
		userId = UserId.random();
		for (int i = 0; i < Session.WORD_NUMBER; i++) {
			SessionWord sessionWord = SessionWordTestBuilder.aSessionWord().build();
			this.sessionWords.add(sessionWord);
		}

	}

	@Test
	void shouldCreateSession() {
		// When
		Session session = getSession();

		// Then
		assertThat(session).isNotNull();
		assertThat(session.getId()).isNotNull();
		assertThat(session.getUserId()).isEqualTo(userId);
		assertThat(session.getDate()).isNotNull();
		assertThat(session.getStatus()).isEqualTo(SessionStatus.INACTIVE);
		assertThat(session.isDeleted()).isFalse();
		assertThat(session.getSessionWords()).isEmpty();
	}

	private Session getSession() {
		return Session.of(userId);
	}

	@Test
	void shouldFailToCreateSessionWhenUserIdIsNull() {
		userId = null;
		assertThatThrownBy(() -> getSession()).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("User id cannot be null");
	}

	@Test
	void shouldBeEqualWhenTheSame() {
		Session session1 = SessionTestBuilder.aSession().build();
		Session session2 = SessionTestBuilder.from(session1).build();

		assertThat(session1).isEqualTo(session2);
	}

	@Test
	void activate_shouldThrowWhenArgumentNull() throws IllegalArgumentException {
		sessionWords = null;

		assertThatThrownBy(() -> getSession().activate(sessionWords)).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("cannot be null");

	}

	@Test
	void activate_shouldThrow_whenWordsSizeIsNotInRequredSize() throws InvalidDomainObjectException {
		sessionWords = Collections.emptySet();

		assertThatThrownBy(() -> getSession().activate(sessionWords)).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessageContaining(Session.WORD_NUMBER + " words is required");
	}

	@Test
	void activate_shouldThrow_whenWordIsAlreadyActive() throws InvalidDomainObjectException {
		Session session = SessionTestBuilder.aSession().withStatus(SessionStatus.ACTIVE).build();

		assertThatThrownBy(() -> session.activate(sessionWords)).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessageContaining("Session is already active");
	}

	@Test
	void activate_shouldThrow_whenWordIsCompleted() throws InvalidDomainObjectException {
		Session session = SessionTestBuilder.aSession().withStatus(SessionStatus.COMPLETED).build();

		assertThatThrownBy(() -> session.activate(sessionWords)).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessageContaining("Session is completed");
	}

}

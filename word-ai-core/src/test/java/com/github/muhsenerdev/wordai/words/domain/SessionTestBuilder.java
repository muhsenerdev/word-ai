package com.github.muhsenerdev.wordai.words.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.github.muhsenerdev.commons.jpa.UserId;

public class SessionTestBuilder {

	private SessionId id;
	private UserId userId;
	private LocalDate date;
	private SessionStatus status;
	private Set<SessionWord> sessionWord;

	public SessionTestBuilder() {
		this.id = SessionId.random();
		this.userId = UserId.random();
		this.date = LocalDate.now();
		this.status = SessionStatus.ACTIVE;
		this.sessionWord = new HashSet<>();
		for (int i = 0; i < Session.WORD_NUMBER; i++) {
			SessionWord word = SessionWordTestBuilder.aSessionWord().build();
			this.sessionWord.add(word);
		}
	}

	public static SessionTestBuilder aSession() {
		return new SessionTestBuilder();
	}

	public static SessionTestBuilder from(Session session) {
		return aSession().withId(session.getId()).withUserId(session.getUserId()).withDate(session.getDate())
				.withStatus(session.getStatus());
	}

	public SessionTestBuilder withId(SessionId id) {
		this.id = id;
		return this;
	}

	public SessionTestBuilder withUserId(UserId userId) {
		this.userId = userId;
		return this;
	}

	public SessionTestBuilder withDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public SessionTestBuilder withStatus(SessionStatus status) {
		this.status = status;
		return this;
	}

	public SessionTestBuilder withSessionWords(Set<SessionWord> words) {
		this.sessionWord = words;
		return this;
	}

	public Session build() {
		return new Session(id, userId, date, status, sessionWord);
	}
}

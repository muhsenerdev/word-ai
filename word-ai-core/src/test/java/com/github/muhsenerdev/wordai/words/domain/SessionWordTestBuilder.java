package com.github.muhsenerdev.wordai.words.domain;

import java.time.Instant;

public class SessionWordTestBuilder {

	private SessionWordId id;
	private WordId wordId;
	private SessionId sessionId;
	private boolean learned;
	private Instant learnedAt;

	public SessionWordTestBuilder() {
		this.id = SessionWordId.random();
		this.wordId = WordId.random();
		this.sessionId = SessionId.random();
		this.learned = false;
		this.learnedAt = null;
	}

	public static SessionWordTestBuilder aSessionWord() {
		return new SessionWordTestBuilder();
	}

	public static SessionWordTestBuilder from(SessionWord sessionWord) {
		return aSessionWord().withId(sessionWord.getId()).withWordId(sessionWord.getWordId())
				.withSessionId(sessionWord.getSessionId()).withLearned(sessionWord.isLearned())
				.withLearnedAt(sessionWord.getLearnedAt());
	}

	public SessionWordTestBuilder withId(SessionWordId id) {
		this.id = id;
		return this;
	}

	public SessionWordTestBuilder withWordId(WordId wordId) {
		this.wordId = wordId;
		return this;
	}

	public SessionWordTestBuilder withSessionId(SessionId sessionId) {
		this.sessionId = sessionId;
		return this;
	}

	public SessionWordTestBuilder withLearned(boolean learned) {
		this.learned = learned;
		return this;
	}

	public SessionWordTestBuilder withLearnedAt(Instant learnedAt) {
		this.learnedAt = learnedAt;
		return this;
	}

	public SessionWord build() {
		return new SessionWord(id, wordId, sessionId, learned, learnedAt);
	}
}

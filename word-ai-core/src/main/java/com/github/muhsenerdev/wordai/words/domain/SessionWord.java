package com.github.muhsenerdev.wordai.words.domain;

import java.time.Instant;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "session_words")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionWord extends SoftDeletableEntity<SessionWordId> {

	public static final String SESSION_WORD_ID_REQUIRED = "session_word.id.required";
	public static final String SESSION_WORD_WORD_ID_REQUIRED = "session_word.word_id.required";
	public static final String SESSION_WORD_SESSION_ID_REQUIRED = "session_word.session_id.required";
	public static final String SESSION_WORD_LEARNED_REQUIRED = "session_word.learned.required";

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
	private SessionWordId id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "word_id", nullable = false))
	private WordId wordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id", insertable = false, updatable = false)
	private Word word;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "session_id", nullable = false))
	private SessionId sessionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_id", insertable = false, updatable = false)
	private Session session;

	@Column(name = "learned", nullable = false)
	private boolean learned;

	@Column(name = "learned_at")
	private Instant learnedAt;

	// For TestBuilder
	protected SessionWord(SessionWordId id, WordId wordId, SessionId sessionId, boolean learned, Instant learnedAt) {
		this.id = id;
		this.wordId = wordId;
		this.sessionId = sessionId;
		this.learned = learned;
		this.learnedAt = learnedAt;

		DomainUtils.notNull(id, "Session word id cannot be null", SESSION_WORD_ID_REQUIRED);
		DomainUtils.notNull(wordId, "Word id cannot be null", SESSION_WORD_WORD_ID_REQUIRED);
		DomainUtils.notNull(sessionId, "Session id cannot be null", SESSION_WORD_SESSION_ID_REQUIRED);
	}

	public static SessionWord of(WordId wordId, SessionId sessionId) {
		return new SessionWord(SessionWordId.random(), wordId, sessionId, false, null);
	}

	@Override
	public SessionWordId getId() {
		return id;
	}
}

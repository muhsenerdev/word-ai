package com.github.muhsenerdev.wordai.words.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.TreeFilter.fptr;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.google.api.client.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sessions")
@SQLDelete(sql = "UPDATE sessions SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Session extends SoftDeletableEntity<SessionId> {

	public static final int WORD_NUMBER = 10;

	public static final String SESSION_ID_REQUIRED = "session.id.required";
	public static final String SESSION_USER_ID_REQUIRED = "session.user_id.required";
	public static final String SESSION_DATE_REQUIRED = "session.date.required";
	public static final String SESSION_STATUS_REQUIRED = "session.status.required";
	public static final String SESSION_WORD_NUMBER = "session.word-number";

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
	private SessionId id;

	@AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
	private UserId userId;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private SessionStatus status;

	@OneToMany(mappedBy = "session", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SessionWord> sessionWords;

	protected Session(SessionId id, UserId userId, LocalDate date, SessionStatus status,
			Set<SessionWord> sessionWords) {
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.status = status;
		this.sessionWords = sessionWords;

		DomainUtils.notNull(id, "Session id cannot be null", SESSION_ID_REQUIRED);
		DomainUtils.notNull(userId, "User id cannot be null", SESSION_USER_ID_REQUIRED);
		DomainUtils.notNull(date, "Date cannot be null", SESSION_DATE_REQUIRED);
		DomainUtils.notNull(status, "Status cannot be null", SESSION_STATUS_REQUIRED);

	}

	@Builder(access = AccessLevel.PUBLIC)
	protected static Session of(UserId userId) {
		return new Session(SessionId.random(), userId, LocalDate.now(), SessionStatus.INACTIVE, new HashSet<>());
	}

	protected static Session create(UserId userId, Set<WordId> words) {
		Assert.notNull(words, "words set cannot be null to create session");
		SessionId random = SessionId.random();
		Set<SessionWord> sessionWords = words.stream().map(wordId -> SessionWord.of(wordId, random))
				.collect(Collectors.toSet());

		Session session = of(userId);
		session.id = random;
		session.activate(sessionWords);
		return session;
	}

	public boolean isInactive() {
		return Objects.equal(this.status, SessionStatus.INACTIVE);
	}

	public boolean isCompleted() {
		return Objects.equal(this.status, SessionStatus.COMPLETED);
	}

	public boolean isActive() {
		return Objects.equal(this.status, SessionStatus.ACTIVE);
	}

	public void activate(Set<SessionWord> sessionWords) {
		Assert.notNull(sessionWords, "sessionWords set cannot be null to activate session");

		if (isActive())
			throw new InvalidDomainObjectException("Session is already active.", "session.activate.active");
		if (isCompleted())
			throw new InvalidDomainObjectException("Session is completed.", "session.activate.completed");

		// DomainUtils.hasSize(sessionWords, WORD_NUMBER, "To activate session,
		// %d words
		// is required.".formatted(WORD_NUMBER),
		// "session.activate.word-number");

		this.status = SessionStatus.ACTIVE;
		this.sessionWords = sessionWords;

	}

	@Override
	public SessionId getId() {
		return id;
	}
}

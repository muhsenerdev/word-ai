package com.github.muhsenerdev.wordai.words.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.jpa.UserId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionDomainService {

	private final SessionRepository sessionRepository;

	public Session startSession(Set<Word> words, UserId userId)
			throws SessionDomainException, IllegalArgumentException {
		Assert.notNull(words, "Words set cannot be null");
		try {
			// 1. User cannot have an active session for today.
			validateSessionStart(userId);
			validateLanguageConsistency(words);
			Set<WordId> wordIds = words.stream().map(Word::getId).collect(Collectors.toSet());
			Session session = Session.create(userId, wordIds);

			return session;
		} catch (InvalidDomainObjectException e) {
			throw new SessionDomainException(e.getMessage(), e.getErrorCode());
		}
	}

	private void validateLanguageConsistency(Set<Word> words) {
		if (words.isEmpty())
			return;

		Language firstLang = words.iterator().next().getLanguage();

		boolean allSame = words.stream().allMatch(w -> w.getLanguage().equals(firstLang));

		if (!allSame) {
			throw new SessionDomainException("All words in a session must belong to the same language.");
		}
	}

	public void validateSessionStart(UserId userId) throws IllegalArgumentException {
		Assert.notNull(userId, "UserId cannot be null");
		int count = sessionRepository.countByUserIdAndDate(userId, LocalDate.now());
		if (count > 0)
			throw new SessionDomainException("User %s has reached daily session limit.".formatted(userId),
					"session.start.daily-limit-reached");
	}

}

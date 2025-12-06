package com.github.muhsenerdev.wordai.words.application.session;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.core.DomainException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.application.LearnerNotFoundException;
import com.github.muhsenerdev.wordai.words.domain.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartSessionCommandHandler {

	private final SessionRepository sessionRepository;

	private final WordSuggestionService suggestionService;
	private final SessionDomainService sessionDomainService;
	private final LearnerRepository learnerRepository;

	@Transactional
	public SessionStartResponse handle(StartSessionCommand command) {
		try {

			UserId userId = UserId.of(command.userId());
			// 1. Check existence of user.
			ensureLearnerExists(userId);

			// 2.0 Before suggestion, check Session can be started. (FAIL-FAST)
			sessionDomainService.validateSessionStart(userId);

			// 2. Suggest words for the user.
			Set<Word> suggestedWords = suggestionService.suggestFor(userId, Session.WORD_NUMBER,
					command.learningLanguage(), command.motherLanguage());

			// 3. Start Session
			Session session = sessionDomainService.startSession(suggestedWords, userId);

			// 4. persist session
			sessionRepository.save(session);

			// 5. Return response
			return SessionStartResponse.builder().sessionId(session.getId()).build();

		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (DomainException e) {
			throw new BusinessValidationException(e.getMessage(), e.getErrorCode(), e);
		} catch (Exception e) {
			throw new SystemException("Failed to start session: " + e.getMessage(), e);
		}
	}

	private void ensureLearnerExists(UserId userId) {
		if (!learnerRepository.existsById(userId)) {
			throw new LearnerNotFoundException(userId);
		}
	}

}

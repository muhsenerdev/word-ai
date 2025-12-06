package com.github.muhsenerdev.wordai.words.application.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.application.LearnerNotFoundException;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.LearnerRepository;
import com.github.muhsenerdev.wordai.words.domain.Session;
import com.github.muhsenerdev.wordai.words.domain.SessionDomainException;
import com.github.muhsenerdev.wordai.words.domain.SessionDomainService;
import com.github.muhsenerdev.wordai.words.domain.SessionRepository;
import com.github.muhsenerdev.wordai.words.domain.SessionTestBuilder;
import com.github.muhsenerdev.wordai.words.domain.Word;
import com.github.muhsenerdev.wordai.words.domain.WordTestBuilder;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class StartSessionCommandHandlerTest {

	@InjectMocks
	private StartSessionCommandHandler handler;

	@Mock
	private SessionRepository sessionRepository;

	@Mock
	private WordSuggestionService suggestionService;

	@Mock
	private SessionDomainService sessionDomainService;

	@Mock
	private LearnerRepository learnerRepository;

	private UUID userId;
	private StartSessionCommand command;
	private Set<Word> suggestedWords;
	private Session session;

	@BeforeEach
	void setUp() {
		userId = UUID.randomUUID();
		command = WordTestData.startSessionCommandWithUserId(userId);

		// Setup suggested words
		suggestedWords = new HashSet<>();
		for (int i = 0; i < Session.WORD_NUMBER; i++) {
			suggestedWords.add(WordTestBuilder.aWord().language(Language.TURKISH).build());
		}

		// Setup session
		session = SessionTestBuilder.aSession().withUserId(UserId.of(userId)).build();
	}

	@Test
	@DisplayName("Should successfully start session when all validations pass")
	void handle_shouldStartSession_whenValidCommand() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(true);
		doNothing().when(sessionDomainService).validateSessionStart(any(UserId.class));
		when(suggestionService.suggestFor(any(UserId.class), anyInt(), any(Language.class), any(Language.class)))
				.thenReturn(suggestedWords);
		when(sessionDomainService.startSession(any(Set.class), any(UserId.class))).thenReturn(session);
		when(sessionRepository.save(any(Session.class))).thenReturn(session);

		// When
		SessionStartResponse response = handler.handle(command);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.sessionId()).isEqualTo(session.getId());

		verify(learnerRepository).existsById(UserId.of(userId));
		verify(sessionDomainService).validateSessionStart(UserId.of(userId));
		verify(suggestionService).suggestFor(eq(UserId.of(userId)), eq(Session.WORD_NUMBER),
				eq(command.learningLanguage()), eq(command.motherLanguage()));
		verify(sessionDomainService).startSession(eq(suggestedWords), eq(UserId.of(userId)));
		verify(sessionRepository).save(session);
	}

	@Test
	@DisplayName("Should throw LearnerNotFoundException when learner does not exist")
	void handle_shouldThrowLearnerNotFoundException_whenLearnerDoesNotExist() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(false);

		// When
		Throwable throwable = catchThrowable(() -> handler.handle(command));

		// Then
		assertThat(throwable).isInstanceOf(LearnerNotFoundException.class);

		verify(learnerRepository).existsById(UserId.of(userId));
		verify(sessionDomainService, never()).validateSessionStart(any(UserId.class));
		verify(suggestionService, never()).suggestFor(any(), anyInt(), any(), any());
		verify(sessionDomainService, never()).startSession(any(), any());
		verify(sessionRepository, never()).save(any());
	}

	@Test
	@DisplayName("Should throw BusinessValidationException when user already has active session")
	void handle_shouldThrowBusinessValidationException_whenActiveSessionExists() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(true);
		doThrow(new SessionDomainException("User has an active session already.", "session.start.active-exists"))
				.when(sessionDomainService).validateSessionStart(any(UserId.class));

		// When
		Throwable throwable = catchThrowable(() -> handler.handle(command));

		// Then
		assertThat(throwable).isInstanceOf(BusinessValidationException.class)
				.hasMessageContaining("User has an active session already.");

		verify(learnerRepository).existsById(UserId.of(userId));
		verify(sessionDomainService).validateSessionStart(UserId.of(userId));
		verify(suggestionService, never()).suggestFor(any(), anyInt(), any(), any());
		verify(sessionDomainService, never()).startSession(any(), any());
		verify(sessionRepository, never()).save(any());
	}

	@Test
	@DisplayName("Should throw BusinessValidationException when domain validation fails during session start")
	void handle_shouldThrowBusinessValidationException_whenDomainValidationFails() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(true);
		doNothing().when(sessionDomainService).validateSessionStart(any(UserId.class));
		when(suggestionService.suggestFor(any(UserId.class), anyInt(), any(Language.class), any(Language.class)))
				.thenReturn(suggestedWords);
		when(sessionDomainService.startSession(any(Set.class), any(UserId.class)))
				.thenThrow(new SessionDomainException("All words in a session must belong to the same language."));

		// When
		Throwable throwable = catchThrowable(() -> handler.handle(command));

		// Then
		assertThat(throwable).isInstanceOf(BusinessValidationException.class)
				.hasMessageContaining("All words in a session must belong to the same language.");

		verify(sessionRepository, never()).save(any());
	}

	@Test
	@DisplayName("Should throw SystemException when unexpected error occurs")
	void handle_shouldThrowSystemException_whenUnexpectedErrorOccurs() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(true);
		doNothing().when(sessionDomainService).validateSessionStart(any(UserId.class));
		when(suggestionService.suggestFor(any(UserId.class), anyInt(), any(Language.class), any(Language.class)))
				.thenThrow(new RuntimeException("Database connection failed"));

		// When
		Throwable throwable = catchThrowable(() -> handler.handle(command));

		// Then
		assertThat(throwable).isInstanceOf(SystemException.class).hasMessageContaining("Failed to start session");

		verify(sessionRepository, never()).save(any());
	}

	@Test
	@DisplayName("Should call services in correct order (fail-fast pattern)")
	void handle_shouldCallServicesInCorrectOrder() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(true);
		doNothing().when(sessionDomainService).validateSessionStart(any(UserId.class));
		when(suggestionService.suggestFor(any(UserId.class), anyInt(), any(Language.class), any(Language.class)))
				.thenReturn(suggestedWords);
		when(sessionDomainService.startSession(any(Set.class), any(UserId.class))).thenReturn(session);
		when(sessionRepository.save(any(Session.class))).thenReturn(session);

		// When
		handler.handle(command);

		// Then - Verify order of execution
		var inOrder = org.mockito.Mockito.inOrder(learnerRepository, sessionDomainService, suggestionService,
				sessionRepository);

		inOrder.verify(learnerRepository).existsById(any(UserId.class));
		inOrder.verify(sessionDomainService).validateSessionStart(any(UserId.class));
		inOrder.verify(suggestionService).suggestFor(any(), anyInt(), any(), any());
		inOrder.verify(sessionDomainService).startSession(any(), any());
		inOrder.verify(sessionRepository).save(any());
	}

	@Test
	@DisplayName("Should pass correct parameters to word suggestion service")
	void handle_shouldPassCorrectParametersToSuggestionService() {
		// Given
		when(learnerRepository.existsById(any(UserId.class))).thenReturn(true);
		doNothing().when(sessionDomainService).validateSessionStart(any(UserId.class));
		when(suggestionService.suggestFor(any(UserId.class), anyInt(), any(Language.class), any(Language.class)))
				.thenReturn(suggestedWords);
		when(sessionDomainService.startSession(any(Set.class), any(UserId.class))).thenReturn(session);
		when(sessionRepository.save(any(Session.class))).thenReturn(session);

		// When
		handler.handle(command);

		// Then
		verify(suggestionService).suggestFor(eq(UserId.of(userId)), eq(Session.WORD_NUMBER), eq(Language.TURKISH), // learningLanguage
				eq(Language.ENGLISH) // motherLanguage
		);
	}
}

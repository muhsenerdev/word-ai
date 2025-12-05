package com.github.muhsenerdev.wordai.words.application.words;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.wordai.words.domain.Word;
import com.github.muhsenerdev.wordai.words.domain.WordFactory;
import com.github.muhsenerdev.wordai.words.domain.WordRepository;
import com.github.muhsenerdev.wordai.words.domain.WordTestBuilder;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

@ExtendWith(MockitoExtension.class)
@DisplayName("WordApplicationServiceImplTest")
@SuppressWarnings("null")
class WordApplicationServiceImplTest {

	@Mock
	private WordRepository wordRepository;

	@Mock
	private WordFactory wordFactory;

	@Mock
	private ApplicationEventPublisher publisher;

	@InjectMocks
	private WordApplicationServiceImpl wordApplicationService;

	private Word word1;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(wordApplicationService, "MAX_INSERTION", 10);
		word1 = WordTestBuilder.aWord().build();
	}

	@SuppressWarnings("unchecked")
	@Test
	void shouldBulkInsertWordsSuccessfully() {
		// Given
		int count = 2;
		BulkInsertWordCommand command = WordTestData.bulkInsertWordCommand(count);
		List<Word> words = List.of(word1, word1);

		when(wordFactory.createWord(any(), any(), any(), any())).thenReturn(word1);
		when(wordRepository.saveAll(any(List.class))).thenReturn(words);

		// When
		BulkWordInsertionResponse response = wordApplicationService.bulkInsert(command);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.responses()).hasSize(count);
		verify(wordFactory, times(count)).createWord(any(), any(), any(), any());
		verify(wordRepository).saveAll(any(List.class));
		verify(publisher).publishEvent(any(WordsInsertedEvent.class));
	}

	@Test
	void shouldReturnEmptyResponseWhenCommandListIsEmpty() {
		// Given
		BulkInsertWordCommand command = BulkInsertWordCommand.builder().commands(Collections.emptyList()).build();

		// When
		BulkWordInsertionResponse response = wordApplicationService.bulkInsert(command);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.responses()).isEmpty();
		verifyNoInteractions(wordFactory, wordRepository, publisher);
	}

	@Test
	void shouldThrowBusinessValidationExceptionWhenMaxInsertionExceeded() {
		// Given
		ReflectionTestUtils.setField(wordApplicationService, "MAX_INSERTION", 1);
		BulkInsertWordCommand command = WordTestData.bulkInsertWordCommand(2);

		// When
		Throwable throwable = catchThrowable(() -> wordApplicationService.bulkInsert(command));

		// Then
		assertThat(throwable).isInstanceOf(BusinessValidationException.class)
				.hasMessageContaining("Too many word insertions");

		verifyNoInteractions(wordFactory, wordRepository, publisher);
	}

	@Test
	void shouldThrowBusinessValidationExceptionWhenDomainExceptionOccurs() {
		// Given
		BulkInsertWordCommand command = WordTestData.bulkInsertWordCommand(1);
		when(wordFactory.createWord(any(), any(), any(), any()))
				.thenThrow(new InvalidDomainObjectException("Invalid word", "invalid.word"));

		// When
		Throwable throwable = catchThrowable(() -> wordApplicationService.bulkInsert(command));

		// Then
		assertThat(throwable).isInstanceOf(BusinessValidationException.class);
	}

	@Test
	void shouldThrowSystemExceptionWhenUnexpectedExceptionOccurs() {
		// Given
		BulkInsertWordCommand command = WordTestData.bulkInsertWordCommand(1);
		when(wordFactory.createWord(any(), any(), any(), any())).thenThrow(new RuntimeException("Unexpected error"));

		// When
		Throwable throwable = catchThrowable(() -> wordApplicationService.bulkInsert(command));

		// Then
		assertThat(throwable).isInstanceOf(SystemException.class).hasMessageContaining("Failed to builk-insert words");
	}
}

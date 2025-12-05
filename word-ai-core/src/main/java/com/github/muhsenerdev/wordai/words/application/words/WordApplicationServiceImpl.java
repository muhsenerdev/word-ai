package com.github.muhsenerdev.wordai.words.application.words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.muhsenerdev.commons.core.DomainException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.wordai.words.domain.CEFR;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.PartOfSpeech;
import com.github.muhsenerdev.wordai.words.domain.Word;
import com.github.muhsenerdev.wordai.words.domain.WordFactory;
import com.github.muhsenerdev.wordai.words.domain.WordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class WordApplicationServiceImpl implements WordApplicationService {

	@Value("${words.max-insertion}")
	private int MAX_INSERTION;

	private final WordRepository wordRepository;
	private final WordFactory wordFactory;
	private final ApplicationEventPublisher publisher;

	@Override
	@Transactional
	public BulkWordInsertionResponse bulkInsert(BulkInsertWordCommand command) {
		try {
			// 1. Ignore Fast
			List<InsertWordCommand> inserts = command.commands();
			if (inserts == null || inserts.isEmpty()) {
				return BulkWordInsertionResponse.builder().responses(Collections.emptyList()).build();
			}

			// 2. Check Max Insertion
			if (inserts.size() > MAX_INSERTION) {
				throw new BusinessValidationException(
						"Too many word insertions: " + inserts.size() + ". Allowed max: " + MAX_INSERTION, "too-many-word-insert");
			}

			// 3. Process Insertions

			List<WordInsertionResponse> responses = new ArrayList<>();
			List<Word> words = new ArrayList<>();
			inserts.forEach(insert -> {

				// 3.1 Create Word
				PartOfSpeech pos = PartOfSpeech.fromString(insert.partOfSpeech());
				Language language = Language.fromString(insert.language());
				CEFR levelCefr = CEFR.fromString(insert.cefrLevel());
				Word word = wordFactory.createWord(insert.text(), pos, levelCefr, language);

				// 3.2 Save Word
				// word = wordRepository.save(word);
				words.add(word);

				// 3.3 Add Response
				responses.add(WordInsertionResponse.builder().id(word.getId().getValue()).build());
			});

			// 3.4 Save Words
			wordRepository.saveAll(words);

			// 4. Publish Event
			publisher.publishEvent(WordsInsertedEvent.builder().words(words).build());

			// 5. Return Response
			return BulkWordInsertionResponse.builder().responses(responses).build();

		} catch (BusinessValidationException e) {
			throw e;
		} catch (DomainException e) {
			throw new BusinessValidationException(e.getMessage(), e.getErrorCode(), e);
		} catch (Exception e) {
			throw new SystemException("Failed to builk-insert words, due to: " + e.getMessage(), e);
		}

	}
}

package com.github.muhsenerdev.wordai.words.application.session;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.Word;
import com.github.muhsenerdev.wordai.words.domain.WordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WordSuggestionService {

	private final WordRepository wordRepository;

	// TODO: Optimize there
	@Transactional(readOnly = true)
	public Set<Word> suggestFor(UserId userId, int number, Language learningLanguage, Language motherLanguage) {
		Assert.notNull(userId, "UserId cannot be null to suggest word");
		Assert.positive(number, "Suggestion number must be positive");
		Assert.notNull(learningLanguage, "learning language cannot be null");
		Assert.notNull(motherLanguage, "motherLanuge cannot be null");
		// * t
		long start = System.currentTimeMillis();
		Set<Word> words = wordRepository.findRandomNewWordsForUser(userId, motherLanguage, number);
		long end = System.currentTimeMillis();
		log.info("Time taken to suggest words: {} ms", end - start);
		return words;
	}

}

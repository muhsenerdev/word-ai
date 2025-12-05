package com.github.muhsenerdev.wordai.words.application.words;

import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;
import com.github.muhsenerdev.wordai.words.domain.WordId;

public class WordNotFoundException extends ResourceNotFoundException {

	public WordNotFoundException(WordId id) {
		super("word", "id", id.getValue(), "word.not-found.id");
	}
}

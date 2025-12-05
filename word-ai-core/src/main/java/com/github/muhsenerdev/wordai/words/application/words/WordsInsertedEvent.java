package com.github.muhsenerdev.wordai.words.application.words;

import java.time.Instant;
import java.util.List;

import com.github.muhsenerdev.commons.core.BaseApplicationEvent;
import com.github.muhsenerdev.wordai.words.domain.Word;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WordsInsertedEvent extends BaseApplicationEvent {
	@NonNull
	private final List<Word> words;

	@Builder
	public WordsInsertedEvent(List<Word> words, Object source) {
		super(Instant.now(), source);
		this.words = words;
	}

}

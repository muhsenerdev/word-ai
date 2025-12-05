package com.github.muhsenerdev.wordai.words.application.words;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.wordai.words.domain.CEFR;
import com.github.muhsenerdev.wordai.words.domain.PartOfSpeech;
import com.github.muhsenerdev.wordai.words.domain.WordId;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class WordData {

	@NonNull
	private final WordId wordId;
	@NonNull
	private final PartOfSpeech pos;
	@NonNull
	private final String text;
	@NonNull
	private final CEFR level;

	@Builder(toBuilder = true)
	public WordData(WordId wordId, String text, PartOfSpeech pos, CEFR level) {
		Assert.hasText(text, "text cannot be null or empty");
		Assert.notNull(pos, "pos cannot be null");
		Assert.notNull(level, "level cannot be null");
		Assert.notNull(wordId, "wordId cannot be null");
		this.wordId = wordId;
		this.text = text;
		this.pos = pos;
		this.level = level;
	}

}

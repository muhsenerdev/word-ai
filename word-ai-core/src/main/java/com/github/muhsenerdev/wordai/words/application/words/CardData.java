package com.github.muhsenerdev.wordai.words.application.words;

import java.util.List;

import com.github.muhsenerdev.wordai.words.domain.WordId;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class CardData {

	private WordId wordId;
	private String sourceSentence;
	private String targetSentences;
	private List<MappingData> mappings;

}

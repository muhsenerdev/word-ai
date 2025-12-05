package com.github.muhsenerdev.wordai.words.application.words;

import java.util.List;

import com.github.muhsenerdev.wordai.words.domain.Language;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CardGenerationRequest {

	private Language sourceLanguage;
	private Language targetLanguage;
	private List<WordData> words;
}

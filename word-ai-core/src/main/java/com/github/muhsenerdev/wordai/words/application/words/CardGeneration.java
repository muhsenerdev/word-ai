package com.github.muhsenerdev.wordai.words.application.words;

import java.util.List;

import com.github.muhsenerdev.wordai.words.domain.Language;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class CardGeneration {

	private Language sourceLanguage;
	private Language targetLanguage;
	private List<CardData> cards;

}

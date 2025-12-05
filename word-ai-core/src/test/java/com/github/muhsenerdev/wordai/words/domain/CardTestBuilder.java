package com.github.muhsenerdev.wordai.words.domain;

import java.util.Collections;
import java.util.List;

public class CardTestBuilder {

	private CardId id;
	private String sourceSentence;
	private String targetSentence;
	private List<Mapping> mappings;
	private Language targetLanguage;
	private WordId wordId;

	public CardTestBuilder() {
		this.id = CardId.random();
		this.sourceSentence = "This is a test sentence.";
		this.targetSentence = "Bu bir test cümlesidir.";
		this.mappings = Collections.singletonList(new Mapping("test", "test"));
		this.targetLanguage = Language.TURKISH;
		this.wordId = WordId.random();
	}

	public CardTestBuilder withId(CardId id) {
		this.id = id;
		return this;
	}

	public CardTestBuilder withSourceSentence(String sourceSentence) {
		this.sourceSentence = sourceSentence;
		return this;
	}

	public CardTestBuilder withTargetSentence(String targetSentence) {
		this.targetSentence = targetSentence;
		return this;
	}

	public CardTestBuilder withMappings(List<Mapping> mappings) {
		this.mappings = mappings;
		return this;
	}

	public CardTestBuilder withTargetLanguage(Language targetLanguage) {
		this.targetLanguage = targetLanguage;
		return this;
	}

	public CardTestBuilder withWordId(WordId wordId) {
		this.wordId = wordId;
		return this;
	}

	public static CardTestBuilder aCard() {
		return new CardTestBuilder();
	}

	public static CardTestBuilder from(Card card) {
		return aCard().withId(card.getId()).withSourceSentence(card.getSourceSentence())
				.withTargetSentence(card.getTargetSentence()).withMappings(card.getMappings())
				.withTargetLanguage(card.getTargetLanguage()).withWordId(card.getWordId());
	}

	public Card build() {
		return new Card(id, sourceSentence, targetSentence, mappings, targetLanguage, wordId);
	}
}

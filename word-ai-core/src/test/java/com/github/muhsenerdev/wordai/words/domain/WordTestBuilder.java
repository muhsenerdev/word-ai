package com.github.muhsenerdev.wordai.words.domain;

import java.util.UUID;

public class WordTestBuilder {

	private WordId id;
	private String text;
	private String slug;
	private PartOfSpeech partOfSpeech;
	private CEFR cefrLevel;
	private Language language;

	public WordTestBuilder() {
		this.id = WordId.random();
		this.text = "test-word-" + UUID.randomUUID().toString().substring(0, 8);
		this.slug = "test-slug-" + UUID.randomUUID().toString().substring(0, 8);
		this.partOfSpeech = PartOfSpeech.NOUN;
		this.cefrLevel = CEFR.A1;
		this.language = Language.ENGLISH;
	}

	public WordTestBuilder id(WordId id) {
		this.id = id;
		return this;
	}

	public WordTestBuilder text(String text) {
		this.text = text;
		return this;
	}

	public WordTestBuilder slug(String slug) {
		this.slug = slug;
		return this;
	}

	public WordTestBuilder partOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
		return this;
	}

	public WordTestBuilder cefrLevel(CEFR cefrLevel) {
		this.cefrLevel = cefrLevel;
		return this;
	}

	public WordTestBuilder language(Language language) {
		this.language = language;
		return this;
	}

	public static WordTestBuilder aWord() {
		return new WordTestBuilder();
	}

	public static WordTestBuilder from(Word word) {
		return aWord().id(word.getId()).text(word.getText()).slug(word.getSlug()).partOfSpeech(word.getPartOfSpeech())
				.cefrLevel(word.getCefrLevel()).language(word.getLanguage());
	}

	public Word build() {
		return new Word(id, text, slug, partOfSpeech, cefrLevel, language);
	}
}

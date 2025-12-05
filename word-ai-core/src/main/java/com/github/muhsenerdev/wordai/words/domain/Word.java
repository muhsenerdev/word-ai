package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "words")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE words SET deleted_at = NOW() WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Word extends SoftDeletableEntity<WordId> {

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
	private WordId id;

	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "slug", nullable = false)
	private String slug;

	@Enumerated(EnumType.STRING)
	@Column(name = "part_of_speech", nullable = false)
	private PartOfSpeech partOfSpeech;

	@Enumerated(EnumType.STRING)
	@Column(name = "cefr_level", nullable = false)
	private CEFR cefrLevel;

	@Enumerated(EnumType.STRING)
	@Column(name = "language", nullable = false)
	private Language language;

	@OneToMany(mappedBy = "word", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Card> cards;

	// For TestBuilder
	protected Word(WordId id, String text, String slug, PartOfSpeech partOfSpeech, CEFR cefrLevel, Language language) {
		this.id = id;
		this.text = text;
		this.slug = slug;
		this.partOfSpeech = partOfSpeech;
		this.cefrLevel = cefrLevel;
		this.language = language;

		DomainUtils.notNull(text, "Word text is required.", "word.text.required");
		DomainUtils.notNull(slug, "Word slug is required.", "word.slug.required");
		DomainUtils.checkLength(slug, 1, 255, "Word slug must be between 1 and 255 characters.", "word.slug.length");
		DomainUtils.notNull(partOfSpeech, "Word partOfSpeech is required.", "word.part-of-speech.required");
		DomainUtils.notNull(cefrLevel, "Word cefrLevel is required.", "word.cefr-level.required");
		DomainUtils.notNull(language, "Word language is required.", "word.language.required");
	}

	@Builder(access = AccessLevel.PROTECTED)
	private static Word create(String text, String slug, PartOfSpeech partOfSpeech, CEFR cefrLevel, Language language) {
		return new Word(WordId.random(), text, slug, partOfSpeech, cefrLevel, language);
	}

}

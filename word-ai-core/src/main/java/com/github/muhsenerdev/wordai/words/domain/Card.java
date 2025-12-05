package com.github.muhsenerdev.wordai.words.domain;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Type;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE cards SET deleted_at = NOW() WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Card extends SoftDeletableEntity<CardId> {

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
	private CardId id;

	@Column(name = "source_sentence", nullable = false)
	private String sourceSentence;

	@Column(name = "target_sentence", nullable = false)
	private String targetSentence;

	@Type(JsonType.class)
	@Column(name = "mappings", nullable = false)
	private List<Mapping> mappings;

	@Enumerated(EnumType.STRING)
	@Column(name = "target_language", nullable = false)
	private Language targetLanguage;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "word_id", nullable = false))
	private WordId wordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id", insertable = false, updatable = false)
	private Word word;

	// For TestBuilder
	protected Card(CardId id, String sourceSentence, String targetSentence, List<Mapping> mappings,
			Language targetLanguage, WordId wordId) {
		this.id = id;
		this.sourceSentence = sourceSentence;
		this.targetSentence = targetSentence;
		this.mappings = mappings;
		this.targetLanguage = targetLanguage;
		this.wordId = wordId;

		DomainUtils.notNull(sourceSentence, "Source sentence is required.", "card.source-sentence.required");
		DomainUtils.notNull(targetSentence, "Target sentence is required.", "card.target-sentence.required");
		DomainUtils.notEmpty(mappings, "Mappings are required.", "card.mappings.required");
		DomainUtils.notNull(targetLanguage, "Target language is required.", "card.target-language.required");
		DomainUtils.notNull(wordId, "Word ID is required.", "card.word-id.required");
	}

	@Builder
	public static Card create(String sourceSentence, String targetSentence, List<Mapping> mappings,
			Language targetLanguage, WordId wordId) {
		return new Card(CardId.random(), sourceSentence, targetSentence, mappings, targetLanguage, wordId);
	}
}

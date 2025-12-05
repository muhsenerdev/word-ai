package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WordId extends BaseEmbeddableId<UUID> implements Serializable {

	private WordId(UUID value) {
		super(value);
	}

	public static WordId of(UUID value) {
		return new WordId(value);
	}

	public static WordId random() {
		return new WordId(UUID.randomUUID());
	}
}

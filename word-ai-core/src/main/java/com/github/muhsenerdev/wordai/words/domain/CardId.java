package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CardId extends BaseEmbeddableId<UUID> implements Serializable {

	private CardId(UUID value) {
		super(value);
	}

	public static CardId of(UUID value) {
		return new CardId(value);
	}

	public static CardId random() {
		return new CardId(UUID.randomUUID());
	}
}

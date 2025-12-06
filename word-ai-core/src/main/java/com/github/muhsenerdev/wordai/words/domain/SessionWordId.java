package com.github.muhsenerdev.wordai.words.domain;

import java.io.Serializable;
import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionWordId extends BaseEmbeddableId<UUID> implements Serializable {

	private SessionWordId(UUID value) {
		super(value);
	}

	public static SessionWordId of(UUID value) {
		return new SessionWordId(value);
	}

	public static SessionWordId random() {
		return new SessionWordId(UUID.randomUUID());
	}

}

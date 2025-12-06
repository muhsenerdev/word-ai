package com.github.muhsenerdev.wordai.words.domain;

import java.io.Serializable;
import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionId extends BaseEmbeddableId<UUID> implements Serializable {

	private SessionId(UUID value) {
		super(value);
	}

	public static SessionId of(UUID value) {
		return new SessionId(value);
	}

	public static SessionId random() {
		return new SessionId(UUID.randomUUID());
	}

}

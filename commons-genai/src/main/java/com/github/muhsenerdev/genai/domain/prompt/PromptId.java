package com.github.muhsenerdev.genai.domain.prompt;

import java.io.Serializable;
import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PromptId extends BaseEmbeddableId<UUID> implements Serializable {

	private PromptId(UUID value) {
		super(value);
	}

	public static PromptId of(UUID value) {
		return new PromptId(value);
	}

	public static PromptId random() {
		return new PromptId(UUID.randomUUID());
	}

}

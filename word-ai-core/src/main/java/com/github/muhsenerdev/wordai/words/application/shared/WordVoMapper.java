package com.github.muhsenerdev.wordai.words.application.shared;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.core.SingleValueObject;
import com.github.muhsenerdev.commons.jpa.BaseEmbeddableId;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.SessionId;

@Component
public class WordVoMapper {

	public Language toLanguage(String source) {
		return Optional.ofNullable(source).map(Language::fromString).orElse(null);
	}

	public UserId toUserId(UUID source) {
		return Optional.ofNullable(source).map(UserId::of).orElse(null);
	}

	public SessionId toSessionId(UUID source) {
		return Optional.ofNullable(source).map(SessionId::of).orElse(null);
	}

	public <T> T fromId(BaseEmbeddableId<T> source) {
		return source == null ? null : source.getValue();
	}

	public <T> T fromSingleVO(SingleValueObject<T> source) {
		return source == null ? null : source.getValue();
	}
}

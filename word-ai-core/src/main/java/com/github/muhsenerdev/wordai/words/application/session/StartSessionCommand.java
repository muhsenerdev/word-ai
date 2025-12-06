package com.github.muhsenerdev.wordai.words.application.session;

import java.util.UUID;

import com.github.muhsenerdev.wordai.words.domain.Language;

import lombok.Builder;

@Builder(toBuilder = true)
public record StartSessionCommand(UUID userId, Language motherLanguage, Language learningLanguage) {

}

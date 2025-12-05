package com.github.muhsenerdev.wordai.words.application.words;

import java.util.UUID;

import lombok.Builder;

@Builder(toBuilder = true)
public record WordInsertionResponse(UUID id) {
}

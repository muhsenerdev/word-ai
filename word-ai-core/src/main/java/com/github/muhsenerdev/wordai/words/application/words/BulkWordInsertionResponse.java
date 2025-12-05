package com.github.muhsenerdev.wordai.words.application.words;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record BulkWordInsertionResponse(List<WordInsertionResponse> responses) {
}

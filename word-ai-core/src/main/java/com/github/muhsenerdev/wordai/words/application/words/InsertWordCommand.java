package com.github.muhsenerdev.wordai.words.application.words;

import lombok.Builder;

@Builder(toBuilder = true)
public record InsertWordCommand(String text, String partOfSpeech, String language, String cefrLevel) {
}

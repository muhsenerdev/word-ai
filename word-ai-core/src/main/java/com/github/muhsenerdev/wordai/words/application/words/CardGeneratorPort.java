package com.github.muhsenerdev.wordai.words.application.words;

public interface CardGeneratorPort {

	CardGeneration generate(CardGenerationRequest request) throws CardGenerationException;
}

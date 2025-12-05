package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.wordai.words.application.words.BulkInsertWordCommand;
import com.github.muhsenerdev.wordai.words.application.words.BulkWordInsertionResponse;
import com.github.muhsenerdev.wordai.words.application.words.InsertWordCommand;
import com.github.muhsenerdev.wordai.words.application.words.WordInsertionResponse;

@Component
public class WordRestMapper {

	public InsertWordCommand toInsertCommand(InsertWordInput source) {
		if (source == null) {
			return null;
		}
		return InsertWordCommand.builder().text(source.getText()).partOfSpeech(source.getPartOfSpeech())
				.language(source.getLanguage()).cefrLevel(source.getCefrLevel()).build();
	}

	public WordInsertionOutput toWordInsertionOutput(WordInsertionResponse source) {
		if (source == null) {
			return null;
		}
		return WordInsertionOutput.builder().id(source.id()).build();
	}

	public BulkWordInsertionOutput toBulkWordInsertionOutput(BulkWordInsertionResponse source) {
		if (source == null) {
			return null;
		}
		List<WordInsertionOutput> responses = source.responses() == null ? Collections.emptyList()
				: source.responses().stream().map(this::toWordInsertionOutput).collect(Collectors.toList());
		return BulkWordInsertionOutput.builder().responses(responses).build();
	}

	public BulkInsertWordCommand toBulkInsertWordCommand(BulkInsertWordInput source) {
		if (source == null) {
			return null;
		}
		List<InsertWordCommand> commands = source.getWords() == null ? Collections.emptyList()
				: source.getWords().stream().map(this::toInsertCommand).collect(Collectors.toList());
		return BulkInsertWordCommand.builder().commands(commands).build();
	}

}

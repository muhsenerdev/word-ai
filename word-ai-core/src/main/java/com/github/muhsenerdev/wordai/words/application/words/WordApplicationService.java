package com.github.muhsenerdev.wordai.words.application.words;

public interface WordApplicationService {

	BulkWordInsertionResponse bulkInsert(BulkInsertWordCommand command);

}

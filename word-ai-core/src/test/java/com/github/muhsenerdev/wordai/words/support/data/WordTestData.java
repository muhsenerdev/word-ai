package com.github.muhsenerdev.wordai.words.support.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.muhsenerdev.wordai.words.application.CreateLearnerCommand;
import com.github.muhsenerdev.wordai.words.application.LearnerCreationResponse;
import com.github.muhsenerdev.wordai.words.application.words.BulkInsertWordCommand;
import com.github.muhsenerdev.wordai.words.application.words.InsertWordCommand;

public class WordTestData {

    public static CreateLearnerCommand createLearnerCommand() {
        return CreateLearnerCommand.builder().userId(UUID.randomUUID()).motherLanguage("ENGLISH")
                .targetLanguage("TURKISH").build();
    }

    public static LearnerCreationResponse learnerCreationResponse() {
        return LearnerCreationResponse.builder().userId(UUID.randomUUID()).build();
    }

    public static InsertWordCommand insertWordCommand() {
        return InsertWordCommand.builder().text("hello").partOfSpeech("NOUN").cefrLevel("A1").language("ENGLISH")
                .build();
    }

    public static BulkInsertWordCommand bulkInsertWordCommand(int count) {
        List<InsertWordCommand> commands = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            commands.add(insertWordCommand());
        }
        return BulkInsertWordCommand.builder().commands(commands).build();
    }

    public static com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words.InsertWordInput insertWordInput() {
        return com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words.InsertWordInput.builder().text("hello")
                .partOfSpeech("NOUN").cefrLevel("A1").language("ENGLISH").build();
    }

    public static com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words.BulkInsertWordInput bulkInsertWordInput(
            int count) {
        List<com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words.InsertWordInput> inputs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inputs.add(insertWordInput());
        }
        return com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words.BulkInsertWordInput.builder()
                .words(inputs).build();
    }
}


package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.muhsenerdev.wordai.words.application.words.BulkInsertWordCommand;
import com.github.muhsenerdev.wordai.words.application.words.BulkWordInsertionResponse;
import com.github.muhsenerdev.wordai.words.application.words.WordApplicationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
@Tag(name = "Words", description = "Word operations")
public class WordController {

	private final WordRestMapper mapper;
	private final WordApplicationService service;

	@PostMapping("/bulk")
	@BulkInsertWordsDocs
	public ResponseEntity<BulkWordInsertionOutput> insertWords(@Valid @RequestBody BulkInsertWordInput input) {
		BulkInsertWordCommand command = mapper.toBulkInsertWordCommand(input);
		BulkWordInsertionResponse response = service.bulkInsert(command);
		return ResponseEntity.ok(mapper.toBulkWordInsertionOutput(response));
	}

}

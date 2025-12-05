package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Schema(description = "Input data for bulk inserting words")
public class BulkInsertWordInput {

	@NotEmpty(message = "words.required")
	@Valid
	@Schema(description = "List of words to insert")
	private List<InsertWordInput> words;
}

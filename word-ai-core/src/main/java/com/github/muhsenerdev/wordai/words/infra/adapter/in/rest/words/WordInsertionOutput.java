package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Schema(description = "Output data for a word insertion")
public class WordInsertionOutput {

	@Schema(description = "The ID of the inserted word")
	private UUID id;
}

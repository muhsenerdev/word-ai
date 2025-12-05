package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Schema(description = "Output data for bulk word insertion")
public class BulkWordInsertionOutput {

	@Schema(description = "List of inserted word responses")
	private List<WordInsertionOutput> responses;
}

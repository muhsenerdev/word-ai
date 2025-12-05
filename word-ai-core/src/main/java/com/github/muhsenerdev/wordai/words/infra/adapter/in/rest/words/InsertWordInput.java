package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Schema(description = "Input data for inserting a new word")
public class InsertWordInput {

	@NotBlank(message = "word.text.required")
	@Schema(description = "The word text", example = "hello")
	private String text;

	@NotBlank(message = "word.part_of_speech.required")
	@JsonProperty("part_of_speech")
	@Schema(description = "The part of speech of the word", example = "NOUN")
	private String partOfSpeech;

	@NotBlank(message = "word.language.required")
	@Schema(description = "The language of the word", example = "ENGLISH")
	private String language;

	@NotBlank(message = "word.cefr_level.required")
	@JsonProperty("cefr_level")
	@Schema(description = "The CEFR level of the word", example = "A1")
	private String cefrLevel;
}

package com.github.muhsenerdev.wordai.words.application.words;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class MappingData {

	private String source;
	private String target;

}

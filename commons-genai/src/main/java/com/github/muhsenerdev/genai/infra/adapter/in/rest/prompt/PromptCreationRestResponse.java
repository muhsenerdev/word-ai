package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Data
public class PromptCreationRestResponse {

    private UUID id;

    private String slug;
}

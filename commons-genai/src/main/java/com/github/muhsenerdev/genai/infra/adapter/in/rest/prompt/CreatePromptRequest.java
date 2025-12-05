package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
@Data
public class CreatePromptRequest {

    private String name;

    private String slug;

    private String provider;

    private String model;

    @JsonProperty("system_message")
    private String systemMessage;

    @JsonProperty("user_message_template")
    private String userMessageTemplate;

    @JsonProperty("input_schema")
    private String inputSchema;

    @JsonProperty("output_schema")
    private String outputSchema;

    @JsonProperty("model_options")
    private Map<String, Object> modelOptions;

    @JsonProperty("output_type")
    private String outputType;

}

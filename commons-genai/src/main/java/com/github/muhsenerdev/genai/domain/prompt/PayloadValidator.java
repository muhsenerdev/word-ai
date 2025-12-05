package com.github.muhsenerdev.genai.domain.prompt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PayloadValidator {

    private final JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
    private final ObjectMapper objectMapper;

    public void validateOrThrow(@NotNull Map<String, Object> input, @NotNull PayloadSchema blueprint) {
        try {

            JsonSchema schema = factory.getSchema(blueprint.getValue());
            String inputString = objectMapper.writeValueAsString(input);
            JsonNode inputNode = objectMapper.readTree(inputString);

            Set<ValidationMessage> errors = schema.validate(inputNode);
            if (!errors.isEmpty())
                throw new PayloadValidationException("Failed to validate payload: " + errors);

        } catch (PayloadValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new PayloadValidationException(e.getMessage(), e);
        }

    }

    public void validateOrThrow(JsonNode inputNode, @NotNull PayloadSchema blueprint) {
        try {
            // 1. Create Schema from blueprint.
            JsonSchema schema = factory.getSchema(blueprint.getValue());

            Set<ValidationMessage> errors = schema.validate(inputNode);
            if (!errors.isEmpty())
                throw new PayloadValidationException("Failed to validate payload: " + errors);

        } catch (PayloadValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new PayloadValidationException(e.getMessage(), e);
        }

    }

}

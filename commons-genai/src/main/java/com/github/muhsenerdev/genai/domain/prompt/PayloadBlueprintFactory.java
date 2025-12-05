package com.github.muhsenerdev.genai.domain.prompt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Set;

@Component
public class PayloadBlueprintFactory {

    private final ObjectMapper objectMapper;
    private final JsonSchemaFactory jsonSchemaFactory;
    private final JsonSchema metaSchema;

    public PayloadBlueprintFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        this.metaSchema = jsonSchemaFactory.getSchema(URI.create("http://json-schema.org/draft-07/schema#"));
    }

    public PayloadBlueprint create(String rawSchema) throws InvalidDomainObjectException {
        Assert.hasText(rawSchema, "rawSchema cannot be null or blank!");

        try {
            // 1. Syntax Check
            JsonNode schemaNode = objectMapper.readTree(rawSchema);

            // 2. Meta-validation
            Set<ValidationMessage> errors = metaSchema.validate(schemaNode);
            if (!errors.isEmpty()) {
                throw new InvalidDomainObjectException("Invalid JSON Schema rules: " + errors,
                        "payload-blueprint.invalid");
            }

            // 3. Library Parsing Check
            jsonSchemaFactory.getSchema(schemaNode);

            // 4. It is OK :)
            return new PayloadBlueprint(rawSchema);

        } catch (Exception e) {
            throw new InvalidDomainObjectException("Failed to parse schema: " + e.getMessage());
        }
    }
}

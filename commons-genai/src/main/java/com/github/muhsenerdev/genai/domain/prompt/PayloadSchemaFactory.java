package com.github.muhsenerdev.genai.domain.prompt;

import java.net.URI;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

@Component
public class PayloadSchemaFactory {

	private final JsonSchemaFactory jsonSchemaFactory;
	private final JsonSchema metaSchema;

	public PayloadSchemaFactory() {
		this.jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
		this.metaSchema = jsonSchemaFactory.getSchema(URI.create("http://json-schema.org/draft-07/schema#"));
	}

	public PayloadSchema create(JsonNode rawSchema) throws InvalidDomainObjectException {
		Assert.notNull(rawSchema, "rawSchema cannot be null!");

		try {

			// 1. Meta-validation
			Set<ValidationMessage> errors = metaSchema.validate(rawSchema);
			if (!errors.isEmpty()) {
				throw new InvalidDomainObjectException("Invalid JSON Schema rules: " + errors, "payload-schema.invalid");
			}

			// 2. Library Parsing Check
			jsonSchemaFactory.getSchema(rawSchema);

			// 3. It is OK :)
			return new PayloadSchema(rawSchema);

		} catch (Exception e) {
			throw new InvalidDomainObjectException("Failed to parse schema: " + e.getMessage(), "payload-schema.invalid");
		}
	}
}
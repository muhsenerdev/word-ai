package com.github.muhsenerdev.genai.domain.prompt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PayloadSchemaFactoryTest {

	private PayloadSchemaFactory factory;
	private ObjectMapper mapper;

	@BeforeEach
	void setUp() {
		factory = new PayloadSchemaFactory();
		mapper = new ObjectMapper();
	}

	@Test
	@DisplayName("should create schema from valid json schema")
	void shouldCreateSchemaFromValidJson() throws Exception {
		String json = """
				{
				    "$schema": "http://json-schema.org/draft-07/schema#",
				    "type": "object",
				    "properties": {
				        "name": {
				            "type": "string"
				        }
				    },
				    "required": ["name"]
				}
				""";
		JsonNode node = mapper.readTree(json);

		PayloadSchema schema = factory.create(node);

		assertThat(schema).isNotNull();
		assertThat(schema.getValue()).isEqualTo(node);
	}

	@Test
	@DisplayName("should fail when schema is null")
	void shouldFailWhenSchemaIsNull() {
		assertThatThrownBy(() -> factory.create(null)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("rawSchema cannot be null!");
	}

	@Test
	@DisplayName("should fail when schema is invalid json schema")
	void shouldFailWhenSchemaIsInvalid() throws Exception {
		// "type" must be a string or array of strings, 123 is invalid
		String invalidJson = """
				{
				    "type": 123
				}
				""";
		JsonNode node = mapper.readTree(invalidJson);

		assertThatThrownBy(() -> factory.create(node)).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessageContaining("Invalid JSON Schema rules");
	}
}

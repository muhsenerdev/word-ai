package com.github.muhsenerdev.genai.domain.prompt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PayloadSchemaTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("should create payload schema with valid value")
	void shouldCreatePayloadSchema() {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", "object");

		PayloadSchema schema = new PayloadSchema(node);

		assertThat(schema).isNotNull();
		assertThat(schema.getValue()).isEqualTo(node);
	}

	@Test
	@DisplayName("should fail when value is null")
	void shouldFailWhenValueIsNull() {
		assertThatThrownBy(() -> new PayloadSchema(null)).isInstanceOf(InvalidDomainObjectException.class)
				.hasMessage("Value of Paylaod Schema cannot be null");
	}

}

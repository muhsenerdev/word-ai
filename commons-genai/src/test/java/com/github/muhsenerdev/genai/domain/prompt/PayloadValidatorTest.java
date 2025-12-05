package com.github.muhsenerdev.genai.domain.prompt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.genai.support.config.TestBeans;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PayloadValidatorTest {
	private PayloadValidator validator = TestBeans.payloadValidator();
	private PayloadSchemaFactory factory = TestBeans.payloadSchemaFactory();

	private static final JsonNode simpleSchemaNode;

	static {
		try {
			simpleSchemaNode = new ObjectMapper().readTree("""
					{
					"type": "object",
					"properties": {
					"productName": { "type": "string", "minLength": 3 },
					"price": { "type": "number", "minimum": 10.0 },
					"tags": { "type": "array" }
					},
					"required": ["productName", "price"]
					}
					""");
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse simple schema JSON", e);
		}
	}

	Map<String, Object> invalidInput;
	Map<String, Object> validInput;

	PayloadSchema blueprint;

	@BeforeEach
	void setUp() {
		blueprint = factory.create(simpleSchemaNode);
		invalidInput = new HashMap<>();
		invalidInput.put("productName", "TV");
		invalidInput.put("price", 5.0);
		invalidInput.put("tags", "elektronik,ucuz");

		validInput = Map.of("productName", "Laptop", "price", 11, "tags", List.of("cart", "curt"));

	}

	@Test
	void givenInvalidInput_shouldThrowException() {
		PayloadValidationException ex = assertThrows(PayloadValidationException.class,
				() -> validator.validateOrThrow(invalidInput, blueprint));

		String errorMessage = ex.getMessage();
		assertTrue(errorMessage.contains("productName"));
		assertTrue(errorMessage.contains("tags"));
		assertTrue(errorMessage.contains("price"));

	}

	@Test
	void givenValidInput_success() {
		assertDoesNotThrow(() -> validator.validateOrThrow(validInput, blueprint));

	}
}
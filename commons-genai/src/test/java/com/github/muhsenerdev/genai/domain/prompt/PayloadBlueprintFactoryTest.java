package com.github.muhsenerdev.genai.domain.prompt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.genai.support.config.TestBeans;

import static org.junit.jupiter.api.Assertions.*;

class PayloadBlueprintFactoryTest {

  @Autowired
  private PayloadBlueprintFactory factory;

  @BeforeEach
  void setUp() {
    factory = TestBeans.payloadBlueprintFactory();
  }

  @Test
  void setup_is_ok() {
    assertNotNull(factory, "Factory must be initialized properly.");
  }

  @Test
  @DisplayName("Broken Json")
  void givenBrokenJson_thenThrowsException() {
    String brokenJson = "{\"type\": \"object\", \"properties\": { \"name\": { \"type\": \"string\" } ";

    InvalidDomainObjectException ex = assertThrows(InvalidDomainObjectException.class,
        () -> factory.create(brokenJson));

    assertTrue(ex.getMessage().contains("Failed to parse"), "Error message should mention about parsing error.");

  }

  @Test
  @DisplayName("Invalid enum type, dragon is unknown.")
  void givenSchemaWithInvalidEnumType_thenThrowsException() {
    String dragonSchema = """
        {
          "type": "object",
          "properties": {
            "hero": { "type": "dragon" }
          }
        }
        """;
    InvalidDomainObjectException ex = assertThrows(InvalidDomainObjectException.class,
        () -> factory.create(dragonSchema));

    assertTrue(ex.getMessage().contains("have a value in the enumeration"),
        "Error message should mention about unknown enumeration");

  }

  @Test
  @DisplayName("When wrong type is provided. For example, 'required' expects 'array', but provided 'string'")
  void givenPropertyHasWrongType_thenThrowsException() {
    String wrongTypeSchema = """
        {
          "type": "object",
          "properties": {
            "name": { "type": "string" }
          },
          "required": "name"
        }
        """;

    InvalidDomainObjectException ex = assertThrows(InvalidDomainObjectException.class,
        () -> factory.create(wrongTypeSchema));

    assertTrue(ex.getMessage().contains("string found, array expected"),
        "Error message should mention about unexpected type.");

  }

  @Test
  @DisplayName("Happy path")
  void givenValidSchema_shouldReturnBlueprint() {
    String validSchema = """
        {
          "type": "object",
          "required": ["name"],
          "properties": {
            "name": { "type": "string", "minLength": 2 }
          }
        }
        """;

    var blueprint = factory.create(validSchema);

    assertNotNull(blueprint);
    assertEquals(validSchema, blueprint.getRawSchemaContent());
  }
}
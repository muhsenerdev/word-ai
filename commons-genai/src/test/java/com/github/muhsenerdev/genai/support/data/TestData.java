package com.github.muhsenerdev.genai.support.data;

import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprint;
import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprintFactory;
import com.github.muhsenerdev.genai.support.config.TestBeans;

public class TestData {

  private static final PayloadBlueprintFactory BLUEPRINT_FACTORY = TestBeans.payloadBlueprintFactory();

  public static PayloadBlueprint payloadBlueprint() {
    return BLUEPRINT_FACTORY.create("""
        {
          "type": "object",
          "required": ["name"],
          "properties": {
            "name": { "type": "string", "minLength": 2 }
          }
        }
        """);
  }

  public static Slug slug() {
    return Slug.of("test-slug-" + UUID.randomUUID().toString().substring(0, 8));
  }
}

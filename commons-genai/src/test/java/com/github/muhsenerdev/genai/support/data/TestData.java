package com.github.muhsenerdev.genai.support.data;

import java.util.UUID;

import com.github.muhsenerdev.commons.jpa.Slug;

import com.github.muhsenerdev.genai.domain.prompt.PayloadSchema;
import com.github.muhsenerdev.genai.domain.prompt.PayloadSchemaFactory;
import com.github.muhsenerdev.genai.support.config.TestBeans;

public class TestData {

  private static final PayloadSchemaFactory SCHEMA_FACTORY = TestBeans.payloadSchemaFactory();

  public static PayloadSchema payloadSchema() {
    return SCHEMA_FACTORY.create(null); // TODO: Pass a Json Schema as JsonNode, instead 'null'
  }

  public static Slug slug() {
    return Slug.of("test-slug-" + UUID.randomUUID().toString().substring(0, 8));
  }
}

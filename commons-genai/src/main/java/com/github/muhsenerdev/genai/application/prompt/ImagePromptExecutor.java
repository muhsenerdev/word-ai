// package com.github.muhsenerdev.genai.application.prompt;

// import com.github.muhsenerdev.genai.domain.prompt.PayloadBlueprint;
// import com.github.muhsenerdev.genai.domain.prompt.PayloadValidator;
// import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
// import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;
// import com.google.genai.Client;
// import com.google.genai.types.*;
// import lombok.RequiredArgsConstructor;
// import org.jetbrains.annotations.NotNull;
// import org.springframework.ai.chat.prompt.PromptTemplate;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import java.util.Map;
// import java.util.Objects;
// import java.util.Optional;

// @Component
// @RequiredArgsConstructor
// public class ImagePromptExecutor implements PromptExecutorStrategy {

// private final PayloadValidator inputValidator;
// @Value("${ai.image.api-key}")
// private String apiKey;
// @Value("${ai.image.api-version}")
// private String apiVersion;

// @Value("${ai.image.model}")
// private String model;

// @Override
// public boolean supports(PromptOutputType outputType) {
// return Objects.equals(PromptOutputType.IMAGE, outputType);
// }

// @Override
// public AiResponse execute(PromptDefinition definition, Map<String, Object>
// inputVariables)
// throws PromptExecutionException {
// try {
// PromptOutputType outputType = definition.getOutputType();
// if (!supports(outputType))
// throw new RuntimeException("Unsupported output type: " + outputType + ", this
// strategy requires IMAGE");

// PayloadBlueprint inputSchema = definition.getInputSchema();
// inputValidator.validateOrThrow(inputVariables, inputSchema);
// String finalUserMessage = prepareUserMessage(definition, inputVariables);

// HttpOptions httpOptions = HttpOptions.builder().apiVersion(apiVersion)
// .retryOptions(HttpRetryOptions.builder().attempts(1).httpStatusCodes(408,
// 429)).build();

// try (Client client =
// Client.builder().apiKey(apiKey).httpOptions(httpOptions).build()) {

// GenerateContentConfig config =
// GenerateContentConfig.builder().responseModalities("TEXT", "IMAGE")
// .build();

// GenerateContentResponse response = client.models.generateContent(model,
// finalUserMessage, config);

// for (Part part : response.parts()) {
// Optional<Blob> inlineData = part.inlineData();
// if (inlineData.isPresent()) {
// Blob blob = inlineData.get();
// Optional<byte[]> imageBytes = blob.data();

// if (imageBytes.isPresent()) {

// return ImageResponse.builder().bytes(imageBytes.get())
// .displayName(blob.displayName().orElse(null)).mimeType(blob.mimeType().orElse(null))
// .build();
// }

// }
// }

// // TODO : fix here!
// throw new RuntimeException("Unexpected error happened");

// } catch (Exception e) {
// throw new RuntimeException(e.getMessage(), e);
// }

// } catch (Exception e) {
// throw new PromptExecutionException("Failed to execute prompt with slug: %s,
// due to :%s"
// .formatted(definition.getSlug(), e.getMessage()), e);
// }
// }

// private @NotNull String prepareUserMessage(PromptDefinition promptDef,
// Map<String, Object> inputMap) {
// return renderUserMessage(promptDef, inputMap);

// }

// private String renderUserMessage(PromptDefinition promptDef, Map<String,
// Object> inputMap) {
// PromptTemplate promptTemplate = new
// PromptTemplate(promptDef.getUserMessageTemplate());
// return promptTemplate.render(inputMap);

// }
// }

// package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
// import
// com.github.muhsenerdev.genai.application.prompt.CreatePromptDefCommand;
// import
// com.github.muhsenerdev.genai.application.prompt.PromptDefCreationResponse;
// import com.github.muhsenerdev.genai.support.data.PromptTestData;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// @ExtendWith(SpringExtension.class)
// class PromptRestMapperTest {

// @Autowired
// PromptRestMapper mapper;

// CreatePromptRequest request;

// PromptDefCreationResponse response;

// @BeforeEach
// void setUp() {
// request = PromptTestData.aCreatePromptRequest().build();
// response = PromptTestData.aPromptDefCreationResponse().build();
// }

// @Test
// @DisplayName("to Create Command - Success")
// void toCreateCommand_success() {
// CreatePromptDefCommand command = mapper.toCreateCommand(request);

// assertEquals(request.getName(), command.name());
// assertEquals(request.getProvider(), command.provider().name());
// assertEquals(request.getSlug(), command.slug().getValue());
// assertEquals(request.getModel(), command.model());
// assertEquals(request.getSystemMessage(), command.systemMessage());
// assertEquals(request.getUserMessageTemplate(),
// command.userMessageTemplate());
// assertEquals(request.getInputSchema(),
// command.inputSchema().getRawSchemaContent());
// assertEquals(request.getOutputSchema(),
// command.outputSchema().getRawSchemaContent());
// assertEquals(request.getModelOptions(), command.modelOptions());
// }

// @Test
// @DisplayName("to Create Command - When Schema Broken -> ThrowsException")
// void toCreateCommand_whenSchemaIsBroken_thenThrowsIllegalObjectException() {
// request =
// PromptTestData.aCreatePromptRequest().inputSchema("broken-schema").build();

// assertThrows(InvalidDomainObjectException.class, () ->
// mapper.toCreateCommand(request));

// }

// @Test
// @DisplayName("to Create Command - When Provider Unknown -> ThrowsException")
// void toCreateCommand_whenProviderUnknown_thenThrowsIllegalObjectException() {
// request =
// PromptTestData.aCreatePromptRequest().provider("unknown-provider").build();

// assertThrows(InvalidDomainObjectException.class, () ->
// mapper.toCreateCommand(request));

// }

// @Test
// @DisplayName("To Rest Response From Prompt Creation Response - SUCCESS")
// void toRestResponseFromCreationResponse_happy_path() {
// PromptCreationRestResponse rest = mapper.toRestResponse(response);

// assertEquals(rest.getId(), response.id());
// assertEquals(rest.getSlug(), response.slug() == null ? null :
// response.slug().getValue());

// }
// }
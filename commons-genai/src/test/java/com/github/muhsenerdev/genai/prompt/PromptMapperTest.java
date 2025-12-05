// package ai.muhsenerdev.genai.application.prompt;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Import;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import ai.muhsenerdev.genai.domain.prompt.PromptCreationDetails;
// import ai.muhsenerdev.genai.support.config.MappersConfiguration;
// import ai.muhsenerdev.genai.support.data.PromptTestData;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// @ExtendWith(SpringExtension.class)
// @Import(MappersConfiguration.class)
// class PromptMapperTest {

// @Autowired
// PromptMapper mapper;

// CreatePromptDefCommand command;

// @BeforeEach
// void setUp() {
// command = PromptTestData.aCreatePromptDefCommand().build();
// }

// @Test
// @DisplayName("Set Up Check - Mapper must be injected")
// void mapper_should_be_injected() {
// assertNotNull(mapper);
// }

// @Test
// void toCreationDetails_happy_path() {
// PromptCreationDetails details = mapper.toCreationDetails(command);

// assertEquals(command.name(), details.name());
// assertEquals(command.slug(), details.slug());
// assertEquals(command.inputSchema(), details.inputSchema());
// assertEquals(command.outputSchema(), details.outputSchema());
// assertEquals(command.model(), details.model());
// assertEquals(command.provider(), details.provider());
// assertEquals(command.systemMessage(), details.systemMessage());
// assertEquals(command.userMessageTemplate(), details.userMessageTemplate());
// assertEquals(command.modelOptions(), details.modelOptions());
// assertEquals(command.outputType(), details.outputType());
// }

// }
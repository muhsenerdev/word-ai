package com.github.muhsenerdev.genai.application.prompt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinitionRepository;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinitionTestBuilder;
import com.github.muhsenerdev.genai.domain.prompt.PromptFactory;
import com.github.muhsenerdev.genai.support.data.PromptTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePromptCommandHandlerTest {

	@Mock
	private PromptFactory factory;

	@Mock
	private PromptDefinitionRepository repository;

	@Mock
	private PromptMapper mapper;

	@InjectMocks
	private CreatePromptCommandHandler handler;

	@Test
	@DisplayName("should create prompt successfully")
	void shouldCreatePromptSuccessfully() {
		// Given
		CreatePromptCommand command = PromptTestData.aCreatePromptCommand();
		PromptDefinition definition = PromptDefinitionTestBuilder.aPromptDefinition().withSlug(command.slug()).build();

		when(repository.existsBySlug(command.slug())).thenReturn(false);
		when(mapper.toCreationData(command)).thenReturn(PromptTestData.aPromptCreationData());
		when(factory.create(any())).thenReturn(definition);
		when(repository.saveAndFlush(any())).thenReturn(definition);

		// When
		PromptCreationResponse response = handler.handle(command);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(definition.getId());
		assertThat(response.slug()).isEqualTo(definition.getSlug());

		verify(repository).existsBySlug(command.slug());
		verify(mapper).toCreationData(command);
		verify(factory).create(any());
		verify(repository).saveAndFlush(definition);
	}

	@Test
	@DisplayName("should fail when prompt with same slug exists")
	void shouldFailWhenDuplicateSlug() {
		// Given
		CreatePromptCommand command = PromptTestData.aCreatePromptCommand();

		when(repository.existsBySlug(command.slug())).thenReturn(true);

		// When/Then
		assertThatThrownBy(() -> handler.handle(command)).isInstanceOf(DuplicatePromptException.class);

		verify(repository).existsBySlug(command.slug());
		// Verify no interactions with factory or save
		verify(factory, org.mockito.Mockito.never()).create(any());
		verify(repository, org.mockito.Mockito.never()).saveAndFlush(any());
	}
}

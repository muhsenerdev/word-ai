package com.github.muhsenerdev.genai.prompt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.DuplicateResourceException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.application.prompt.CreatePromptDefCommand;
import com.github.muhsenerdev.genai.application.prompt.CreatePromptDefDefaultHandler;
import com.github.muhsenerdev.genai.application.prompt.PromptDefCreationResponse;
import com.github.muhsenerdev.genai.application.prompt.PromptMapper;
import com.github.muhsenerdev.genai.domain.prompt.PromptCreationDetails;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinitionRepository;
import com.github.muhsenerdev.genai.domain.prompt.PromptFactory;
import com.github.muhsenerdev.genai.domain.prompt.PromptId;
import com.github.muhsenerdev.genai.support.data.PromptTestData;
import com.github.muhsenerdev.genai.support.data.TestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePromptDefDefaultHandlerTest {

    @Mock
    private PromptFactory factory;

    @Mock
    private PromptDefinitionRepository repository;

    @Mock
    private PromptMapper mapper;

    @InjectMocks
    private CreatePromptDefDefaultHandler handler;

    private CreatePromptDefCommand.CreatePromptDefCommandBuilder commandBuilder;
    private PromptDefinition mockDefinition;
    private PromptCreationDetails mockDetails;
    private static final PromptId TEST_ID = PromptId.random();
    private static final Slug TEST_SLUG = TestData.slug();

    @BeforeEach
    void setUp() {
        commandBuilder = PromptTestData.aCreatePromptDefCommand();
        mockDetails = PromptTestData.aPromptCreationDetails().build();
        mockDefinition = mock(PromptDefinition.class);

        lenient().when(mapper.toCreationDetails(any())).thenReturn(mockDetails);
        lenient().when(factory.create(any())).thenReturn(mockDefinition);
        lenient().when(mockDefinition.getId()).thenReturn(TEST_ID);
        lenient().when(mockDefinition.getSlug()).thenReturn(TEST_SLUG);
        lenient().when(repository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void handle_shouldCreatePromptSuccessfully() {
        // Given
        CreatePromptDefCommand command = commandBuilder.build();
        when(repository.existsBySlug(command.slug())).thenReturn(false);

        // When
        PromptDefCreationResponse response = handler.handle(command);

        // Then
        assertNotNull(response);
        assertEquals(TEST_ID, response.id());
        assertEquals(TEST_SLUG, response.slug());

        verify(repository).existsBySlug(command.slug());
        verify(mapper).toCreationDetails(command);
        verify(factory).create(mockDetails);
        verify(repository).saveAndFlush(mockDefinition);
    }

    @Test
    void handle_shouldThrowDuplicateResourceException_whenSlugExists() {
        // Given
        CreatePromptDefCommand command = commandBuilder.build();
        when(repository.existsBySlug(command.slug())).thenReturn(true);

        // When & Then
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> handler.handle(command));

        assertEquals("prompt", exception.getResource());
        assertEquals("slug", exception.getField());
        assertEquals(command.slug().getValue(), exception.getValue());

        verify(repository).existsBySlug(command.slug());
        verifyNoMoreInteractions(repository, mapper, factory);
    }

    @Test
    void handle_shouldWrapIllegalObjectExceptionInBusinessValidationException() {
        // Given
        CreatePromptDefCommand command = commandBuilder.build();
        when(repository.existsBySlug(command.slug())).thenReturn(false);

        String errorMessage = "Invalid input";
        when(factory.create(any())).thenThrow(new InvalidDomainObjectException(errorMessage));

        // When & Then
        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> handler.handle(command));

        assertEquals(errorMessage, exception.getMessage());
        assertInstanceOf(InvalidDomainObjectException.class, exception.getCause());
    }

    @Test
    void handle_shouldWrapUnexpectedExceptionInSystemException() {
        // Given
        CreatePromptDefCommand command = commandBuilder.build();
        when(repository.existsBySlug(command.slug())).thenReturn(false);

        String errorMessage = "Unexpected error";
        when(factory.create(any())).thenThrow(new RuntimeException(errorMessage));

        // When & Then
        SystemException exception = assertThrows(SystemException.class, () -> handler.handle(command));

        assertTrue(exception.getMessage().contains("Failed to create prompt definition"));
        assertInstanceOf(RuntimeException.class, exception.getCause());
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
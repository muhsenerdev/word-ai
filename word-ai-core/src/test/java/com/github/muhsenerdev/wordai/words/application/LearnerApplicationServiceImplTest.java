package com.github.muhsenerdev.wordai.words.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.wordai.words.domain.Learner;
import com.github.muhsenerdev.wordai.words.domain.LearnerRepository;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class LearnerApplicationServiceImplTest {

    @InjectMocks
    private LearnerApplicationServiceImpl learnerApplicationService;

    @Mock
    private LearnerRepository learnerRepository;

    @Mock
    private LearnerMapper learnerMapper;

    @Test
    void create_shouldCreateLearner_whenCommandIsValid() {
        // Given
        CreateLearnerCommand command = WordTestData.createLearnerCommand();
        LearnerCreationResponse expectedResponse = WordTestData.learnerCreationResponse();

        when(learnerMapper.toResponse(any(Learner.class))).thenReturn(expectedResponse);

        // When
        LearnerCreationResponse response = learnerApplicationService.create(command);

        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(learnerRepository).save(any(Learner.class));
        verify(learnerMapper).toResponse(any(Learner.class));
    }

    @Test
    void create_shouldThrowBusinessValidationException_whenDomainExceptionOccurs() {
        // Given
        CreateLearnerCommand command = WordTestData.createLearnerCommand().toBuilder()
                .motherLanguage("INVALID_LANGUAGE").build();

        // When
        Throwable throwable = catchThrowable(() -> learnerApplicationService.create(command));

        // Then
        assertThat(throwable).isInstanceOf(BusinessValidationException.class);
    }

    @Test
    void create_shouldThrowSystemException_whenUnexpectedExceptionOccurs() {
        // Given
        CreateLearnerCommand command = WordTestData.createLearnerCommand();

        doThrow(new RuntimeException("Unexpected error")).when(learnerRepository).save(any(Learner.class));

        // When
        Throwable throwable = catchThrowable(() -> learnerApplicationService.create(command));

        // Then
        assertThat(throwable).isInstanceOf(SystemException.class).hasMessageContaining("Failed to create learner");
    }
}

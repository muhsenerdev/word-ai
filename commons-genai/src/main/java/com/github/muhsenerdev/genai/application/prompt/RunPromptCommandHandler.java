package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.genai.domain.prompt.PayloadValidationException;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinitionRepository;
import com.github.muhsenerdev.genai.domain.prompt.PromptOutputType;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RunPromptCommandHandler implements RunPromptUseCase {

    private final PromptDefinitionRepository repository;
    private final List<PromptExecutorStrategy> executors;

    @Override
    @Transactional
    public PromptRunResponse handle(RunPromptCommand command)
            throws ResourceNotFoundException, BusinessValidationException, SystemException {
        Slug slug = command.slug();
        try {
            PromptDefinition promptDef = repository.findBySlug(slug)
                    .orElseThrow(() -> new PromptNotFoundException(slug));

            PromptOutputType outputType = promptDef.getOutputType();
            PromptExecutorStrategy executor = getExecutorOrThrow(outputType);

            AiResponse response = executor.execute(promptDef, command.input());

            return PromptRunResponse.builder().output(response).build();

        } catch (ResourceNotFoundException | BusinessValidationException e) {
            throw e;
        } catch (PayloadValidationException e) {
            throw new BusinessValidationException(e.getMessage(), e);
        } catch (Exception e) {
            throw new SystemException("Failed to run prompt: %s, due to: %s".formatted(slug, e.getMessage()), e);
        }
    }

    private PromptExecutorStrategy getExecutorOrThrow(PromptOutputType outputType) {
        return executors.stream().filter(exc -> exc.supports(outputType)).findFirst().orElseThrow(
                () -> new IllegalStateException("No prompt executor found for output type: " + outputType));
    }

}

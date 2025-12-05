package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.genai.domain.prompt.PromptCreationDetails;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinitionRepository;
import com.github.muhsenerdev.genai.domain.prompt.PromptFactory;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.exception.*;
import com.github.muhsenerdev.commons.jpa.Slug;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePromptDefDefaultHandler implements CreatePromptDefUseCase {

    private final PromptFactory factory;
    private final PromptDefinitionRepository repository;
    private final PromptMapper mapper;

    @Override
    @Transactional
    public PromptDefCreationResponse handle(CreatePromptDefCommand command) {
        try {

            Slug slug = command.slug();
            if (repository.existsBySlug(slug)) {
                throw new DuplicatePromptException(slug);
            }

            PromptCreationDetails creationDetails = mapper.toCreationDetails(command);

            PromptDefinition definition = factory.create(creationDetails);

            repository.saveAndFlush(definition);

            return new PromptDefCreationResponse(definition.getId(), definition.getSlug());

        } catch (DuplicatePromptException e) {
            throw e;
        } catch (InvalidDomainObjectException e) {
            throw new BusinessValidationException(e.getMessage(), e.getErrorCode(), e);
        } catch (Exception e) {
            throw new SystemException("Failed to create prompt definition, due to:  " + e.getMessage(), e);
        }
    }
}

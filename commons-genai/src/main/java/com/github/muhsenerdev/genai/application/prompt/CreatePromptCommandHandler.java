package com.github.muhsenerdev.genai.application.prompt;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.domain.prompt.PromptCreationData;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinition;
import com.github.muhsenerdev.genai.domain.prompt.PromptDefinitionRepository;
import com.github.muhsenerdev.genai.domain.prompt.PromptFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreatePromptCommandHandler {
	private final PromptFactory factory;
	private final PromptDefinitionRepository repository;
	private final PromptMapper mapper;

	@Transactional
	public PromptCreationResponse handle(CreatePromptCommand command) {
		try {

			// 1. Check uniqueness of slug.
			Slug slug = command.slug();
			if (repository.existsBySlug(slug)) {
				throw new DuplicatePromptException(slug);
			}

			// 2. Create Prompt Definition
			PromptCreationData creationData = mapper.toCreationData(command);
			PromptDefinition definition = factory.create(creationData);

			// 3. Persist definition
			repository.saveAndFlush(definition);

			// 4. Return response
			return new PromptCreationResponse(definition.getId(), definition.getSlug());

		} catch (DuplicatePromptException e) {
			throw e;
		} catch (InvalidDomainObjectException e) {
			throw new BusinessValidationException(e.getMessage(), e.getErrorCode(), e);
		} catch (Exception e) {
			throw new SystemException("Failed to create prompt definition, due to:  " + e.getMessage(), e);
		}
	}
}

package com.github.muhsenerdev.genai.domain.prompt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.muhsenerdev.commons.jpa.Slug;

@Repository
public interface PromptDefinitionRepository extends JpaRepository<PromptDefinition, PromptId> {

    boolean existsBySlug(Slug slug);

    Optional<PromptDefinition> findBySlug(Slug slug);

}

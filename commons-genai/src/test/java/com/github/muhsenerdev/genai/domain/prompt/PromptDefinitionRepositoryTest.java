package com.github.muhsenerdev.genai.domain.prompt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;
import com.github.muhsenerdev.commons.jpa.Slug;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@EntityScan(basePackages = "com.github.muhsenerdev")
@EnableJpaRepositories(basePackages = "com.github.muhsenerdev")
@EnableJpaAuditing
@Slf4j
class PromptDefinitionRepositoryTest extends BasePersistenceIT {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PromptDefinitionRepository repository;

    @Test
    void should_container_is_started() {
        assertTrue(postgres.isRunning());
    }

    @Test
    @DisplayName("Save - Happy Path")
    void save_success() {
        PromptDefinition def = getADefinition();

        def = repository.saveAndFlush(def);

        entityManager.clear();
        assertNotNull(def.getId());
        Optional<PromptDefinition> byId = repository.findById(def.getId());

        assertTrue(byId.isPresent());
        PromptDefinition found = byId.get();

        assertEquals(found, def, "Saved and found prompt definitions must beequal.");
    }

    private static PromptDefinition getADefinition() {
        return PromptDefinitionTestBuilder.aPromptDefinition().build();
    }

    @Test
    @DisplayName("After deletion, it must be filtered when try to read it with JPA.")
    void delete_should_be_deleted_softly() {
        PromptDefinition def = getADefinition();
        repository.save(def);

        repository.delete(def);

        // Read it with JPA -> Then will be Filtered
        Optional<PromptDefinition> opt = repository.findById(def.getId());
        assertTrue(opt.isEmpty(), "It must be empty, because soft-delete filter is working.");

    }

    @Test
    @DisplayName("ExistsBySlug - Exists")
    public void existsBySlug_whenExists_shouldReturnTrue() {
        PromptDefinition aDefinition = getADefinition();
        repository.saveAndFlush(aDefinition);

        boolean existsBySlug = repository.existsBySlug(aDefinition.getSlug());

        assertTrue(existsBySlug);
    }

    @Test
    @DisplayName("ExistsBySlug - Not Exist")
    void existsBySlug_whenNotExists_shouldReturnFalse() {

        boolean exists = repository.existsBySlug(Slug.of("random-slug"));

        assertFalse(exists);
    }
}
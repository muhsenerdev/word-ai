package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("null")
@EnableJpaAuditing
class CardRepositoryTest extends BasePersistenceIT {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private WordRepository wordRepository;

	private Word word;

	@BeforeEach
	public void setup() {
		cardRepository.deleteAll();
		wordRepository.deleteAll();
		word = WordTestBuilder.aWord().build();
		wordRepository.save(word);
	}

	@Test
	void shouldSaveAndFindCard() {
		// Given

		Card card = CardTestBuilder.aCard().withWordId(word.getId()).build();

		// When
		cardRepository.save(card);

		// Then
		Optional<Card> foundCard = cardRepository.findById(card.getId());
		assertThat(foundCard).isPresent();
		assertThat(foundCard.get().getId()).isEqualTo(card.getId());
		assertThat(foundCard.get().getSourceSentence()).isEqualTo(card.getSourceSentence());
		assertThat(foundCard.get().getTargetSentence()).isEqualTo(card.getTargetSentence());
		assertThat(foundCard.get().getMappings()).hasSize(1);
		assertThat(foundCard.get().getMappings().get(0)).isEqualTo(card.getMappings().get(0));
		assertThat(foundCard.get().getTargetLanguage()).isEqualTo(card.getTargetLanguage());
		assertThat(foundCard.get().getWordId()).isEqualTo(word.getId());
	}

	// should be soft deleted
	@Test
	void shouldSoftDeleteCard() {
		// Given
		Card card = CardTestBuilder.aCard().withWordId(word.getId()).build();
		cardRepository.save(card);
		entityManager.flush();
		entityManager.clear();

		// When
		cardRepository.delete(card);

		// Then
		Optional<Card> foundCard = cardRepository.findById(card.getId());
		assertThat(foundCard).isEmpty();
	}
}

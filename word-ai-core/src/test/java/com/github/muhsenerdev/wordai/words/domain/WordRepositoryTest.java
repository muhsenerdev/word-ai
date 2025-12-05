package com.github.muhsenerdev.wordai.words.domain;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("null")
@EnableJpaAuditing
class WordRepositoryTest extends BasePersistenceIT {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private CardRepository cardRepository;

	@org.junit.jupiter.api.BeforeEach
	void setUp() {
		wordRepository.deleteAll();

	}

	@Test
	void shouldSaveAndFindWord() {
		// Given
		Word word = WordTestBuilder.aWord().build();

		// When
		wordRepository.save(word);

		// Then
		Optional<Word> foundWord = wordRepository.findById(word.getId());
		assertThat(foundWord).isPresent();
		assertThat(foundWord.get().getId()).isEqualTo(word.getId());
		assertThat(foundWord.get().getText()).isEqualTo(word.getText());
		assertThat(foundWord.get().getSlug()).isEqualTo(word.getSlug());
		assertThat(foundWord.get().getPartOfSpeech()).isEqualTo(word.getPartOfSpeech());
		assertThat(foundWord.get().getCefrLevel()).isEqualTo(word.getCefrLevel());
		assertThat(foundWord.get().getLanguage()).isEqualTo(word.getLanguage());
	}

	@Test
	void shouldSoftDeleteWord() {
		// Given
		Word word = WordTestBuilder.aWord().build();
		wordRepository.save(word);

		// When
		wordRepository.delete(word);

		// Then
		Optional<Word> foundWord = wordRepository.findById(word.getId());
		assertThat(foundWord).isEmpty();

	}

	@Test
	void shouldFindWordWithCards() {
		// Given
		Word word = WordTestBuilder.aWord().build();
		Card card = CardTestBuilder.aCard().withWordId(word.getId()).build();
		wordRepository.saveAndFlush(word);
		cardRepository.saveAndFlush(card);
		entityManager.clear();

		// When
		Optional<Word> foundWord = wordRepository.findWithCardsById(word.getId());

		// Then
		assertThat(foundWord).isPresent();
		System.out.println(foundWord.get().getCards());
		assertThat(foundWord.get().getCards().get(0).getId()).isEqualTo(card.getId());
	}

	@Test
	void shouldNotFetchSoftDeletedCards() {
		// Given
		Word word = WordTestBuilder.aWord().build();
		Card card = CardTestBuilder.aCard().withWordId(word.getId()).build();
		wordRepository.saveAndFlush(word);
		cardRepository.saveAndFlush(card);
		entityManager.clear();
		cardRepository.delete(card);
		entityManager.flush();
		entityManager.clear();

		// When
		Optional<Word> foundWord = wordRepository.findWithCardsById(word.getId());

		// Then
		assertThat(foundWord).isPresent();
		assertThat(foundWord.get().getCards()).isEmpty();
	}

	// When deleting a word, it should delete all its cards
	@Test
	void shouldDeleteCardsWhenDeletingWord() {
		// Given
		Word word = WordTestBuilder.aWord().build();
		Card card = CardTestBuilder.aCard().withWordId(word.getId()).build();
		wordRepository.saveAndFlush(word);
		cardRepository.saveAndFlush(card);
		entityManager.clear();

		// When
		System.out.println("***********");
		wordRepository.deleteById(word.getId());
		entityManager.flush();
		entityManager.clear();
		System.out.println("***********");

		// Then
		Optional<Word> foundWord = wordRepository.findById(word.getId());
		assertThat(foundWord).isEmpty();
		Optional<Card> foundCard = cardRepository.findById(card.getId());
		assertThat(foundCard).isEmpty();
	}

}

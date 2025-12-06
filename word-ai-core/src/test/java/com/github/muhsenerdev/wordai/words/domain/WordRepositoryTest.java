package com.github.muhsenerdev.wordai.words.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.github.muhsenerdev.commons.jpa.BasePersistenceIT;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("null")
@EnableJpaAuditing
class WordRepositoryTest extends BasePersistenceIT {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private LearnerRepository learnerRepository;

	@Autowired
	private SessionRepository sessionRepository;

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

	@Test
	void findRandomNewWordsForUser_shouldReturnOnlyUnlearnedWords() {
		Learner learner = LearnerTestBuilder.aLearner().withUserId(WordTestData.MOCK_USER_ID).build();
		learnerRepository.saveAndFlush(learner);

		// Given: Setup 5 words in the database
		Word word1 = WordTestBuilder.aWord().language(Language.ENGLISH).build();
		Word word2 = WordTestBuilder.aWord().language(Language.ENGLISH).build();
		Word word3 = WordTestBuilder.aWord().language(Language.ENGLISH).build();
		Word word4 = WordTestBuilder.aWord().language(Language.ENGLISH).build();
		Word word5 = WordTestBuilder.aWord().language(Language.ENGLISH).build();
		Word word6 = WordTestBuilder.aWord().language(Language.TURKISH).build();

		wordRepository.saveAndFlush(word1);
		wordRepository.saveAndFlush(word2);
		wordRepository.saveAndFlush(word3);
		wordRepository.saveAndFlush(word4);
		wordRepository.saveAndFlush(word5);
		wordRepository.saveAndFlush(word6);
		entityManager.flush();
		entityManager.clear();

		// Given: User has 2 sessions, each with 2 learned words (total 4 learned words)
		com.github.muhsenerdev.commons.jpa.UserId userId = learner.getId();

		// Session 1: User learned word1 and word2
		Session session1 = SessionTestBuilder.aSession().withUserId(userId).withStatus(SessionStatus.INACTIVE).build();
		SessionWord sessionWord1 = SessionWordTestBuilder.aSessionWord().withSessionId(session1.getId())
				.withWordId(word1.getId()).withLearned(true).build();
		SessionWord sessionWord2 = SessionWordTestBuilder.aSessionWord().withSessionId(session1.getId())
				.withWordId(word2.getId()).withLearned(true).build();
		session1 = SessionTestBuilder.from(session1).withSessionWords(Set.of(sessionWord1, sessionWord2)).build();

		// Session 2: User learned word3 and word4
		Session session2 = SessionTestBuilder.aSession().withUserId(userId).withStatus(SessionStatus.INACTIVE).build();
		SessionWord sessionWord3 = SessionWordTestBuilder.aSessionWord().withSessionId(session2.getId())
				.withWordId(word3.getId()).withLearned(true).build();
		SessionWord sessionWord4 = SessionWordTestBuilder.aSessionWord().withSessionId(session2.getId())
				.withWordId(word4.getId()).withLearned(true).build();
		session2 = SessionTestBuilder.from(session2).withSessionWords(Set.of(sessionWord3, sessionWord4)).build();
		// Persist sessions and session words

		sessionRepository.saveAndFlush(session1);
		sessionRepository.saveAndFlush(session2);

		// When: Ask for 5 new words
		java.util.Set<Word> newWords = wordRepository.findRandomNewWordsForUser(userId, Language.ENGLISH, 5);

		// Then: Should return only 1 word (word5), as the other 4 are already learned
		assertThat(newWords).hasSize(1);
		assertThat(newWords).extracting(Word::getId).containsExactly(word5.getId());
	}

}

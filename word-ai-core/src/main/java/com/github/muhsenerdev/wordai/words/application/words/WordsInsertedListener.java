package com.github.muhsenerdev.wordai.words.application.words;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.muhsenerdev.wordai.words.domain.*;
import com.google.api.client.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WordsInsertedListener {

	private final CardRepository cardRepository;

	private final Set<Language> targetLangs = Set.of(Language.TURKISH);

	private final CardGeneratorPort port;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = { WordsInsertedEvent.class })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void onWordsInserted(WordsInsertedEvent event) {

		List<Word> words = event.getWords();
		log.debug("Listening to Words Inserted. Words: '{}'", words.stream().map(Word::getText).toList());

		Map<Language, List<Word>> groupBySourceLanguage = words.stream().collect(Collectors.groupingBy(Word::getLanguage));

		for (Map.Entry<Language, List<Word>> entry : groupBySourceLanguage.entrySet()) {
			Language sourceLang = entry.getKey();
			List<Word> sourceWords = entry.getValue();

			for (Language targetLang : targetLangs) {
				if (Objects.equal(targetLang, sourceLang)) {
					log.debug("Target and source languages are the same, so skipping.");
					continue;
				}

				List<WordData> wordDataList = sourceWords.stream().map(word -> {
					return WordData.builder().wordId(word.getId()).text(word.getText()).level(word.getCefrLevel())
							.pos(word.getPartOfSpeech()).build();
				}).toList();

				CardGenerationRequest request = CardGenerationRequest.builder().targetLanguage(targetLang)
						.sourceLanguage(sourceLang).words(wordDataList).build();
				log.debug("CardGenerationRequest is created for: Source: {}, Target: {},", sourceLang, targetLang);
				log.debug("Calling CardGenerationPort...");
				long start = System.currentTimeMillis();
				CardGeneration response = port.generate(request);
				long end = System.currentTimeMillis();
				log.debug("Cards are generated in {} ms.", (end - start));

				List<CardData> cardDatas = response.getCards();
				List<Card> cards = cardDatas.stream().map(cardData -> {

					List<Mapping> mappings = cardData.getMappings().stream().map(mappingData -> {
						return Mapping.of(mappingData.getSource(), mappingData.getTarget());
					}).toList();

					return Card.builder().mappings(mappings).wordId(cardData.getWordId())
							.sourceSentence(cardData.getSourceSentence()).targetSentence(cardData.getTargetSentences())
							.targetLanguage(targetLang).build();

				}).toList();

				log.debug("Card entities are constructed. Persisting...");
				cardRepository.saveAll(cards);

			}

		}

	}

}

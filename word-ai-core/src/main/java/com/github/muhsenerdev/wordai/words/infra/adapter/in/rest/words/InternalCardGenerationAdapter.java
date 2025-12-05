package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.genai.application.prompt.AiResponse;
import com.github.muhsenerdev.genai.application.prompt.PromptApplicationService;
import com.github.muhsenerdev.genai.application.prompt.PromptRunResponse;
import com.github.muhsenerdev.genai.application.prompt.RunPromptCommand;
import com.github.muhsenerdev.genai.application.prompt.TextResponse;
import com.github.muhsenerdev.wordai.words.application.words.CardData;
import com.github.muhsenerdev.wordai.words.application.words.CardGeneration;
import com.github.muhsenerdev.wordai.words.application.words.CardGenerationException;
import com.github.muhsenerdev.wordai.words.application.words.CardGenerationRequest;
import com.github.muhsenerdev.wordai.words.application.words.CardGeneratorPort;
import com.github.muhsenerdev.wordai.words.application.words.MappingData;
import com.github.muhsenerdev.wordai.words.application.words.WordData;
import com.github.muhsenerdev.wordai.words.application.words.CardData.CardDataBuilder;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.WordId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InternalCardGenerationAdapter implements CardGeneratorPort {

	@Value("${prompts.english-to-turkish}")
	private String englishToTurkishPrompt;

	private final ObjectMapper objectMapper;

	private static final String WORD_INPUT_TEMPLATE = "%s, %s, %s, %s";

	private final PromptApplicationService genAi;

	@Override
	public CardGeneration generate(CardGenerationRequest request) throws CardGenerationException {

		Slug promptSlug = resolvePrompt(request);

		List<WordData> words = request.getWords();

		List<CardData> cards = new ArrayList<>();
		int n = words.size();
		List<WordData> subList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			subList.add(words.get(i));
			if ((i != 0) && (i % 5 == 0 || i == n - 1)) {
				log.debug("Generating cards in chunk of size {}.", subList.size());
				cards.addAll(generateCards(promptSlug, subList));
				subList.clear();
			}
		}

		log.debug("Generated cards for total {} words....", cards.size());
		return CardGeneration.builder().cards(cards).build();

	}

	private List<CardData> generateCards(Slug promptSlug, List<WordData> words) {
		// 1. Create Input
		List<String> inputList = new ArrayList<>();
		words.forEach(word -> {
			inputList.add(renderWord(word));
		});
		Map<String, Object> variables = new HashMap<>();
		variables.put("words", inputList);

		// 2. Run Prompt
		RunPromptCommand command = RunPromptCommand.builder().slug(promptSlug).input(variables).build();

		// 3. Check AI response in a simple way
		PromptRunResponse runResponse = genAi.run(command);
		AiResponse aiResponse = runResponse.output();

		if (!(aiResponse instanceof TextResponse textReponse)) {
			throw new RuntimeException("Invalid response type from AI: " + aiResponse.getType());
		}

		// 4. Read response and construct response
		JsonNode outputNode = objectMapper.valueToTree(textReponse.getOutputMap());
		List<CardData> cards = new ArrayList<>();
		outputNode.get("cards").forEach(cardNode -> {
			CardDataBuilder cardDataBuilder = CardData.builder();
			String sourceSentence = cardNode.get("source_sentence").asText("");
			String targetSentence = cardNode.get("target_sentence").asText("");
			String wordIdString = cardNode.get("word_id").asText();
			cardDataBuilder.wordId(WordId.of(UUID.fromString(wordIdString)));
			cardDataBuilder.sourceSentence(sourceSentence);
			cardDataBuilder.targetSentences(targetSentence);

			List<MappingData> mappings = new ArrayList<>();
			cardNode.get("mappings").forEach(mappingNode -> {
				String source = mappingNode.get("source").asText();
				String target = mappingNode.get("target").asText();
				MappingData mappingData = MappingData.builder().source(source).target(target).build();
				mappings.add(mappingData);
			});

			cardDataBuilder.mappings(mappings);
			cards.add(cardDataBuilder.build());
		});
		return cards;
	}

	// TODO: Fix this simple logic.
	private Slug resolvePrompt(CardGenerationRequest request) {
		Language sourceLang = request.getSourceLanguage();
		Language targetLang = request.getTargetLanguage();

		if (sourceLang != Language.ENGLISH || targetLang != Language.TURKISH) {
			throw new CardGenerationException(
					"Unsuppoerted language pair, source: %s, target %s".formatted(sourceLang, targetLang),
					"card-generation.unsupported-pair");
		}

		return Slug.of(englishToTurkishPrompt);
	}

	public String renderWord(WordData data) {
		return String.format(WORD_INPUT_TEMPLATE, data.getText(), data.getLevel(), data.getPos(),
				data.getWordId().toString());
	}

}

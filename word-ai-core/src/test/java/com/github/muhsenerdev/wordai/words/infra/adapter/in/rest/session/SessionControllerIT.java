package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.muhsenerdev.wordai.words.application.LearnerNotFoundException;
import com.github.muhsenerdev.wordai.words.domain.Session;
import com.github.muhsenerdev.wordai.words.domain.SessionDomainService;
import com.github.muhsenerdev.wordai.words.domain.SessionRepository;
import com.github.muhsenerdev.wordai.words.domain.SessionStatus;
import com.github.muhsenerdev.wordai.words.domain.SessionTestBuilder;
import com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.shared.WordsBaseIT;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;
import com.github.muhsenerdev.wordai.words.support.security.WithCustomMockUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionControllerIT extends WordsBaseIT {

	private static final String PATH = "/api/v1/sessions/start";
	@Autowired
	private SessionRepository repository;

	@BeforeEach
	public void setup() {
		repository.deleteAll();
		super.saveMockUserIfNotExists();
	}

	private Session getActiveSession() {
		return SessionTestBuilder.aSession().withUserId(WordTestData.MOCK_USER_ID)
				.withSessionWords(Collections.emptySet()).withStatus(SessionStatus.ACTIVE).build();
	}

	// When active session exists
	@Test
	@WithCustomMockUser
	public void should_return_400_when_active_session_exists() throws Exception {
		// Given
		repository.save(getActiveSession());

		MvcResult result = perform();

		JsonNode node = extractNode(result);

		assertThat(node.get("errors").get(SessionDomainService.SESSION_ALREADY_ACTIVE_CODE)).isNotNull();

		log.info("{}", result.getResponse().getContentAsString());

		// When & Then

	}

	@Test
	@WithCustomMockUser
	public void should_return_400_when_daily_quota_reached() throws Exception {
		// Given

		for (int i = 0; i < SessionDomainService.DAILY_QUOTA; i++) {
			repository.save(SessionTestBuilder.from(getActiveSession()).withStatus(SessionStatus.COMPLETED).build());
		}
		MvcResult result = perform();

		JsonNode node = extractNode(result);

		assertThat(node.get("errors").get(SessionDomainService.SESSION_DAILY_QUOTA_CODE)).isNotNull();

		log.info("{}", result.getResponse().getContentAsString());

		// When & Then

	}

	@Test
	@WithCustomMockUser(userId = "00001000-0000-0000-0100-000000000001")
	public void should_return404_when_learner_not_found() throws Exception {
		// Given

		MvcResult result = perform(status().is(404));

		JsonNode node = extractNode(result);

		assertThat(node.get("errors").get(LearnerNotFoundException.NOT_FOUND_CODE)).isNotNull();

		log.info("{}", result.getResponse().getContentAsString());

		// When & Then

	}

	private MvcResult perform(ResultMatcher... matchers) throws Exception {

		ResultActions perform = mockMvc
				.perform(MockMvcRequestBuilders.post(PATH).contentType(MediaType.APPLICATION_JSON));

		for (ResultMatcher matcher : matchers) {
			perform.andExpect(matcher);
		}

		return perform.andReturn();

	}

}

package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.genai.application.prompt.PromptApplicationService;
import com.github.muhsenerdev.wordai.users.application.AuthenticationService;
import com.github.muhsenerdev.wordai.users.application.UserApplicationService;
import com.github.muhsenerdev.wordai.words.WordsTestApplication;
import com.github.muhsenerdev.wordai.words.domain.Learner;
import com.github.muhsenerdev.wordai.words.domain.LearnerRepository;
import com.github.muhsenerdev.wordai.words.domain.LearnerTestBuilder;
import com.github.muhsenerdev.wordai.words.support.data.WordTestData;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes = WordsTestApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@EntityScan(basePackages = "com.github.muhsenerdev.wordai.words")
@EnableJpaRepositories(basePackages = "com.github.muhsenerdev.wordai.words")
@ComponentScan(basePackages = { "com.github.muhsenerdev.wordai.words", "com.github.muhsenerdev.commons.core" })
@ActiveProfiles("test")
@SuppressWarnings("null")
@EnableJpaAuditing
@Slf4j
public abstract class WordsBaseIT {

	@MockitoBean
	protected UserApplicationService userApplicationService;

	@MockitoBean
	protected AuthenticationService authenticationService;

	@MockitoBean
	protected PromptApplicationService promptApplicationService;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected LearnerRepository learnerRepository;

	protected Learner getLearnForMockUser() {
		return LearnerTestBuilder.aLearner().withUserId(WordTestData.MOCK_USER_ID).build();
	}

	protected Learner saveMockUserIfNotExists() {
		return learnerRepository.findById(WordTestData.MOCK_USER_ID)
				.orElseGet(() -> learnerRepository.save(getLearnForMockUser()));
	}

	@ServiceConnection
	protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
			.withDatabaseName("word_ai").withUsername("postgres").withPassword("postgres");

	@TestConfiguration
	static class TestSecurityConfig {
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/register").permitAll()
							.requestMatchers("/api/v1/words/bulk").hasAnyRole("ADMIN").anyRequest().authenticated());
			http.exceptionHandling(
					ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
			return http.build();
		}
	}

	@SneakyThrows
	protected JsonNode extractNode(MvcResult result) {
		return objectMapper.readTree(result.getResponse().getContentAsByteArray());
	}

}

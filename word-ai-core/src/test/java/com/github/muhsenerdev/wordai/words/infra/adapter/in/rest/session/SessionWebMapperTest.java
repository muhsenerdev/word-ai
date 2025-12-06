package com.github.muhsenerdev.wordai.words.infra.adapter.in.rest.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.muhsenerdev.wordai.words.application.session.SessionStartResponse;
import com.github.muhsenerdev.wordai.words.application.session.StartSessionCommand;
import com.github.muhsenerdev.wordai.words.application.shared.WordVoMapper;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.domain.SessionId;
import com.github.muhsenerdev.wordai.words.infra.config.SecurityPrincipal;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SessionWebMapperTest.TestConfig.class })
class SessionWebMapperTest {

	@Autowired
	private SessionWebMapper mapper;

	private SecurityPrincipal principal;
	private SessionStartResponse sessionStartResponse;
	private UUID userId;
	private SessionId sessionId;

	@BeforeEach
	void setUp() {
		userId = UUID.randomUUID();
		sessionId = SessionId.random();

		principal = SecurityPrincipal.builder().userId(userId).username("testuser").password("password")
				.motherLanguage(Language.ENGLISH).learningLanguage(Language.TURKISH)
				.authorities(Collections.emptyList()).accountNonExpired(true).accountNonLocked(true)
				.credentialsNonExpired(true).enabled(true).build();

		sessionStartResponse = SessionStartResponse.builder().sessionId(sessionId).build();
	}

	@Test
	@DisplayName("Should map SecurityPrincipal to StartSessionCommand")
	void toStartSessionCommand_shouldMapCorrectly() {
		// When
		StartSessionCommand command = mapper.toStartSessionCommand(principal);

		// Then
		assertThat(command).isNotNull();
		assertThat(command.userId()).isEqualTo(userId);
		assertThat(command.motherLanguage()).isEqualTo(Language.ENGLISH);
		assertThat(command.learningLanguage()).isEqualTo(Language.TURKISH);
	}

	@Test
	@DisplayName("Should return null when SecurityPrincipal is null")
	void toStartSessionCommand_shouldReturnNull_whenPrincipalIsNull() {
		// When
		StartSessionCommand command = mapper.toStartSessionCommand(null);

		// Then
		assertThat(command).isNull();
	}

	@Test
	@DisplayName("Should map SessionStartResponse to StartSessionOutput")
	void toStartSessionOutput_shouldMapCorrectly() {
		// When
		StartSessionOutput output = mapper.toStartSessionOutput(sessionStartResponse);

		// Then
		assertThat(output).isNotNull();
		assertThat(output.sessionId()).isEqualTo(sessionId.getValue());
	}

	@Test
	@DisplayName("Should return null when SessionStartResponse is null")
	void toStartSessionOutput_shouldReturnNull_whenResponseIsNull() {
		// When
		StartSessionOutput output = mapper.toStartSessionOutput(null);

		// Then
		assertThat(output).isNull();
	}

	@Test
	@DisplayName("Should extract all required fields from SecurityPrincipal")
	void toStartSessionCommand_shouldExtractAllFields() {
		// Given
		UUID specificUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
		SecurityPrincipal customPrincipal = SecurityPrincipal.builder().userId(specificUserId).username("customuser")
				.password("custompass").motherLanguage(Language.TURKISH).learningLanguage(Language.ENGLISH)
				.authorities(Collections.emptyList()).accountNonExpired(true).accountNonLocked(true)
				.credentialsNonExpired(true).enabled(true).build();

		// When
		StartSessionCommand command = mapper.toStartSessionCommand(customPrincipal);

		// Then
		assertThat(command).isNotNull();
		assertThat(command.userId()).isEqualTo(specificUserId);
		assertThat(command.motherLanguage()).isEqualTo(Language.TURKISH);
		assertThat(command.learningLanguage()).isEqualTo(Language.ENGLISH);
	}

	@TestConfiguration
	static class TestConfig {
		@Bean
		public WordVoMapper wordVoMapper() {
			return new WordVoMapper();
		}

		@Bean
		public SessionWebMapper sessionWebMapper(WordVoMapper wordVoMapper) {
			return new SessionWebMapper(wordVoMapper);
		}
	}
}

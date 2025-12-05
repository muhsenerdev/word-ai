package com.github.muhsenerdev.wordai.words.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.words.domain.Learner;

@ExtendWith(SpringExtension.class)
@Import(LearnerMapper.class)
class LearnerMapperTest {

	@Autowired
	private LearnerMapper learnerMapper;

	@Test
	@DisplayName("Should map Learner to LearnerCreationResponse correctly")
	void shouldMapLearnerToLearnerCreationResponse() {
		// Given
		Learner learner = mock(Learner.class);
		UserId userId = UserId.random();
		when(learner.getUserId()).thenReturn(userId);

		// When
		LearnerCreationResponse response = learnerMapper.toResponse(learner);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.userId()).isEqualTo(userId.getValue());
	}

	@Test
	@DisplayName("Should return null when Learner is null")
	void shouldReturnNullWhenLearnerIsNull() {
		// When
		LearnerCreationResponse response = learnerMapper.toResponse(null);

		// Then
		assertThat(response).isNull();
	}

	@Test
	@DisplayName("Should map UserId to UUID correctly")
	void shouldMapUserIdToUUID() {
		// Given
		UserId userId = UserId.random();

		// When
		UUID uuid = learnerMapper.map(userId);

		// Then
		assertThat(uuid).isEqualTo(userId.getValue());
	}

	@Test
	@DisplayName("Should return null when UserId is null")
	void shouldReturnNullWhenUserIdIsNull() {
		// When
		UUID uuid = learnerMapper.map(null);

		// Then
		assertThat(uuid).isNull();
	}
}

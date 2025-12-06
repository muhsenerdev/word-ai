package com.github.muhsenerdev.wordai.words.infra.config;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.users.infra.security.TokenClaimCustomizer;
import com.github.muhsenerdev.wordai.words.domain.Learner;
import com.github.muhsenerdev.wordai.words.domain.LearnerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Customizes JWT token claims by adding learner-specific information. Adds
 * mother_language and learning_language claims to the token.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LearnerTokenClaimCustomizer implements TokenClaimCustomizer {

	private final LearnerRepository learnerRepository;

	@Override
	public void customize(Map<String, Object> claims) {
		Object userIdClaim = claims.get("userId");

		if (userIdClaim == null) {
			log.warn("userId claim not found in token, cannot customize with learner information");
			return;
		}

		try {
			UUID userId = UUID.fromString(userIdClaim.toString());
			Optional<Learner> learnerOpt = learnerRepository.findByUserId(UserId.of(userId));

			if (learnerOpt.isPresent()) {
				Learner learner = learnerOpt.get();
				claims.put("mother_language", learner.getMotherLanguage().name());
				claims.put("learning_language", learner.getTargetLanguage().name());
				log.debug("Added learner language claims for user: {}", userId);
			} else {
				log.debug("No learner found for userId: {}, skipping language claims", userId);
			}
		} catch (IllegalArgumentException e) {
			log.error("Invalid userId format in claims: {}", userIdClaim, e);
		}
	}
}

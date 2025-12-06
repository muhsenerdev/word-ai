package com.github.muhsenerdev.wordai.words.infra.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.muhsenerdev.wordai.users.infra.security.JwtPrincipalProvider;
import com.github.muhsenerdev.wordai.words.domain.Language;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides SecurityPrincipal instances from JWT tokens. Extracts user
 * information including language preferences from JWT claims.
 */
@Component
@Slf4j
public class SecurityPrincipalProvider implements JwtPrincipalProvider {

	@Override
	public UserDetails createPrincipal(DecodedJWT decodedJWT) {
		String username = decodedJWT.getClaim("userame").asString(); // Note: typo in original code "userame"
		String userIdValue = decodedJWT.getClaim("userId").asString();
		Claim rolesClaim = decodedJWT.getClaim("roles");
		Claim motherLanguageClaim = decodedJWT.getClaim("mother_language");
		Claim learningLanguageClaim = decodedJWT.getClaim("learning_language");

		// Extract roles
		Set<GrantedAuthority> authorities = new HashSet<>();
		if (!rolesClaim.isNull()) {
			List<String> rolesList = rolesClaim.asList(String.class);
			if (rolesList != null) {
				authorities = rolesList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
			}
		}

		// Extract languages
		Language motherLanguage = null;
		Language learningLanguage = null;

		if (!motherLanguageClaim.isNull()) {
			try {
				motherLanguage = Language.fromString(motherLanguageClaim.asString());
			} catch (Exception e) {
				log.warn("Failed to parse mother_language claim: {}", motherLanguageClaim.asString(), e);
			}
		}

		if (!learningLanguageClaim.isNull()) {
			try {
				learningLanguage = Language.fromString(learningLanguageClaim.asString());
			} catch (Exception e) {
				log.warn("Failed to parse learning_language claim: {}", learningLanguageClaim.asString(), e);
			}
		}

		UUID userId = UUID.fromString(userIdValue);

		SecurityPrincipal principal = SecurityPrincipal.builder().userId(userId).username(username).password("") // Password
																													// not
																													// needed
																													// for
																													// JWT
																													// authentication
				.motherLanguage(motherLanguage).learningLanguage(learningLanguage).authorities(authorities)
				.accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).enabled(true).build();

		log.debug("Created SecurityPrincipal for user: {} with languages: mother={}, learning={}", username,
				motherLanguage, learningLanguage);

		return principal;
	}
}

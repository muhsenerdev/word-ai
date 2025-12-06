package com.github.muhsenerdev.wordai.words.support.security;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.wordai.words.domain.Language;
import com.github.muhsenerdev.wordai.words.infra.config.SecurityPrincipal;

public class WithMockCustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

	@Override
	public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

		List<SimpleGrantedAuthority> roles = Arrays.stream(annotation.roles())
				.map(role -> new SimpleGrantedAuthority(RoleName.of(role).getValue())).toList();

		SecurityPrincipal principal = SecurityPrincipal.builder()
				.motherLanguage(Language.fromString(annotation.motherLanguage())).authorities(roles)
				.learningLanguage(Language.fromString(annotation.learningLanguage()))
				.userId(UUID.fromString(annotation.userId())).username(annotation.username()).build();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null, roles);

		SecurityContextHolder.setContext(securityContext);
		securityContext.setAuthentication(token);

		return securityContext;
	}

}

package com.github.muhsenerdev.wordai.users.infra.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.wordai.users.infra.SecurityProperties;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private SecurityProperties properties;

	@Autowired(required = false)
	private TokenClaimCustomizer tokenClaimCustomizer;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		DefaultPrincipal principal = (DefaultPrincipal) authentication.getPrincipal();
		String username = principal.getUsername();
		UserId userId = principal.getUserId();
		Set<GrantedAuthority> roles = principal.getRoles();

		Map<String, Object> claimsMap = Map.of("username", username, "userId", userId.getValue(), "roles",
				roles.stream().map(GrantedAuthority::getAuthority).toList());

		claimsMap = new HashMap<>(claimsMap);
		if (tokenClaimCustomizer != null) {
			tokenClaimCustomizer.customize(claimsMap);
			log.info("Token claim customized");
		}

		String token = JwtUtils.generateToken(properties.getJwtExpirationMs(), claimsMap, properties.getJwtSecret());

		response.addHeader("Authorization", "Bearer " + token);
		response.flushBuffer();
		log.debug("After successful authentication, JWT token generated for user {}", userId.getValue());
	}

}

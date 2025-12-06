package com.github.muhsenerdev.wordai.users.infra.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.wordai.users.application.GenerateTokenRequest;
import com.github.muhsenerdev.wordai.users.application.SecurityPort;
import com.github.muhsenerdev.wordai.users.infra.SecurityProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityAdapter implements SecurityPort {

	private final SecurityProperties properties;

	@Override
	public String generateAccessToken(GenerateTokenRequest request) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", request.username());
		claims.put("sub", request.userId());
		claims.put("userId", request.userId().toString());
		claims.put("roles", request.roles());
		claims.put("iat", new Date());

		return JwtUtils.generateToken(properties.getJwtExpirationMs(), claims, properties.getJwtSecret());

	}

}

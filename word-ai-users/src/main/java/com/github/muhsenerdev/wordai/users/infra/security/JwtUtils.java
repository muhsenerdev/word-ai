package com.github.muhsenerdev.wordai.users.infra.security;

import java.time.Instant;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

	public static String generateToken(long expiresInMs, Map<String, Object> claims, String secret) {

		Builder builder = JWT.create();
		builder.withExpiresAt(Instant.now().plusMillis(expiresInMs));

		claims.forEach((k, v) -> {
			builder.withClaim(k, v.toString());
		});

		return builder.sign(Algorithm.HMAC256(secret));

	}

	/**
	 * Verifies and decodes a JWT token.
	 * 
	 * @param token  the JWT token to verify
	 * @param secret the secret key used to sign the token
	 * @return the decoded JWT if verification succeeds
	 * @throws JWTVerificationException if verification fails
	 */
	public static DecodedJWT verifyToken(String token, String secret) throws JWTVerificationException {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}

	/**
	 * Extracts the Bearer token from the Authorization header.
	 * 
	 * @param authorizationHeader the Authorization header value
	 * @return the token without the "Bearer " prefix, or null if invalid
	 */
	public static String extractBearerToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}

}

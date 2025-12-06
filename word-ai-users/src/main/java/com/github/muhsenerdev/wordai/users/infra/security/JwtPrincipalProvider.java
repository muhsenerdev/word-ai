package com.github.muhsenerdev.wordai.users.infra.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Functional interface to provide custom UserDetails implementation from JWT
 * token. Modules can implement this interface to provide their own UserDetails
 * implementation.
 */
@FunctionalInterface
public interface JwtPrincipalProvider {

	/**
	 * Creates a UserDetails instance from the decoded JWT token.
	 * 
	 * @param decodedJWT the decoded JWT token containing user claims
	 * @return UserDetails implementation
	 */
	UserDetails createPrincipal(DecodedJWT decodedJWT);
}

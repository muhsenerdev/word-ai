package com.github.muhsenerdev.wordai.users.infra.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.infra.SecurityProperties;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final SecurityProperties securityProperties;

	@Autowired(required = false)
	private JwtPrincipalProvider jwtPrincipalProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");
		String token = JwtUtils.extractBearerToken(authorizationHeader);

		if (token != null) {
			try {
				DecodedJWT decodedJWT = JwtUtils.verifyToken(token, securityProperties.getJwtSecret());

				UserDetails userDetails;
				if (jwtPrincipalProvider != null) {
					// Use custom principal provider if available
					userDetails = jwtPrincipalProvider.createPrincipal(decodedJWT);
					log.debug("Using custom JwtPrincipalProvider to create UserDetails");
				} else {
					// Use default principal
					userDetails = createDefaultPrincipal(decodedJWT);
					log.debug("Using DefaultPrincipal for user: {}", userDetails.getUsername());
				}

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				log.debug("JWT authentication successful for user: {}", userDetails.getUsername());

			} catch (JWTVerificationException e) {
				log.debug("JWT verification failed: {}", e.getMessage());

			} catch (Exception e) {
				log.error("Error processing JWT token", e);
			}
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Creates a DefaultPrincipal from the decoded JWT token.
	 * 
	 * @param decodedJWT the decoded JWT
	 * @return DefaultPrincipal instance
	 */
	private DefaultPrincipal createDefaultPrincipal(DecodedJWT decodedJWT) {
		String username = decodedJWT.getClaim("username").asString();
		String userIdValue = decodedJWT.getClaim("userId").asString();
		Claim rolesClaim = decodedJWT.getClaim("roles");

		Set<RoleName> roles = new HashSet<>();
		if (!rolesClaim.isNull()) {
			List<String> rolesList = rolesClaim.asList(String.class);
			if (rolesList != null) {
				roles = rolesList.stream().map(RoleName::of).collect(Collectors.toSet());
			}
		}

		return new DefaultPrincipal(UserId.of(UUID.fromString(userIdValue)), roles, Username.of(username), null);
	}
}

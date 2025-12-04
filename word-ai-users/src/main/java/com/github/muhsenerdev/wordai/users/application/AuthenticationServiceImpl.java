package com.github.muhsenerdev.wordai.users.application;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.wordai.users.infra.SecurityProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    // WARNING: Problematic depedency direction.
    private final SecurityProperties properties;

    @Override
    // TODO: Make it solid in terms of validations etc.
    public String generateAccessToken(GenerateTokenRequest request) {
        try {
            Builder builder = JWT.create()
                    .withIssuer("wordai")
                    .withSubject(request.userId().toString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + properties.getJwtExpirationMs()))
                    .withClaim("roles", request.roles().stream().toList())
                    .withClaim("username", request.username());

            request.claims().forEach((k, v) -> {
                builder.withClaim(k, v.toString());
            });

            return builder.sign(Algorithm.HMAC256(properties.getJwtSecret().getBytes()));
        } catch (Exception e) {
            throw new SystemException("Unxpected error happened during generating token: " + e.getMessage(), e);
        }

    }

}

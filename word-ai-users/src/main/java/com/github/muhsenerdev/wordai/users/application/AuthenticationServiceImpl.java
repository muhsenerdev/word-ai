package com.github.muhsenerdev.wordai.users.application;

import org.springframework.stereotype.Service;

import com.github.muhsenerdev.commons.core.exception.SystemException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final SecurityPort port;

	public String generateAccessToken(GenerateTokenRequest request) {
		try {

			return port.generateAccessToken(request);

		} catch (Exception e) {
			throw new SystemException("Unxpected error happened during generating token: " + e.getMessage(), e);
		}

	}

}

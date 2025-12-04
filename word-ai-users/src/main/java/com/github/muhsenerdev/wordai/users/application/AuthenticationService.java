package com.github.muhsenerdev.wordai.users.application;

public interface AuthenticationService {
    String generateAccessToken(GenerateTokenRequest request);

}

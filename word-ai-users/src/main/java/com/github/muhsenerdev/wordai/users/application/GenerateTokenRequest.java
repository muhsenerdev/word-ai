package com.github.muhsenerdev.wordai.users.application;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;

@Builder(toBuilder = true)
public record GenerateTokenRequest(UUID userId, String username, Set<String> roles, Map<String, String> claims) {

}

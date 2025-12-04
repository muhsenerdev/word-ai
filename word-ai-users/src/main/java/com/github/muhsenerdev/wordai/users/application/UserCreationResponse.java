package com.github.muhsenerdev.wordai.users.application;

import lombok.Builder;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record UserCreationResponse(UUID id, String username, Set<String> roles) {
}

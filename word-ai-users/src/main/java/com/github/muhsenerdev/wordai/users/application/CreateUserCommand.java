package com.github.muhsenerdev.wordai.users.application;

import lombok.Builder;

@Builder(toBuilder = true)
public record CreateUserCommand(String username, String password) {
}

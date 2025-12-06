package com.github.muhsenerdev.wordai.users.infra.security;

import java.util.Map;

@FunctionalInterface
public interface TokenClaimCustomizer {

	void customize(Map<String, Object> claims);

}

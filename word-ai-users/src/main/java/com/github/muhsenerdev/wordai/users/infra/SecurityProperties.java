package com.github.muhsenerdev.wordai.users.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "security")
@Component
public class SecurityProperties {

	private String jwtSecret;
	private long jwtExpirationMs;
	private String issuer;
	private String loginUrl;
	private String registerUrl;
	private String usernameParameter;
	private String passwordParameter;

}

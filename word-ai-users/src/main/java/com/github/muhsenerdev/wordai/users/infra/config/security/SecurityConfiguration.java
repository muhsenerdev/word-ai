package com.github.muhsenerdev.wordai.users.infra.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.muhsenerdev.wordai.users.infra.SecurityProperties;
import com.github.muhsenerdev.wordai.users.infra.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	private final AccessDeniedHandler accessDeniedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private SecurityProperties properties;

	@Value("${security.register-path}")
	private String registerPath;

	@Value("${security.enabled}")
	private boolean enabled;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		if (!enabled) {
			return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
					.build();
		}

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, registerPath).permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "swagger-ui.html").permitAll()
						.requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/words/bulk").hasAnyRole("ADMIN").anyRequest()
						.authenticated());
		http.exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.accessDeniedHandler(accessDeniedHandler)).httpBasic(httpBasic -> httpBasic.disable());

		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.formLogin(form -> form.loginProcessingUrl(properties.getLoginUrl())
				.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler)
				.usernameParameter(properties.getUsernameParameter())
				.passwordParameter(properties.getPasswordParameter()).permitAll());

		// Add JWT authentication filter before UsernamePasswordAuthenticationFilter
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}

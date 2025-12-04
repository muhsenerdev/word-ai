package com.github.muhsenerdev.wordai.users.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Value("${security.register-path}")
    private String registerPath;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, registerPath).permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "swagger-ui.html").permitAll() // Allow
                                                                                                             // Spring
                                                                                                             // Doc
                                                                                                             // endpoints
                        .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

}

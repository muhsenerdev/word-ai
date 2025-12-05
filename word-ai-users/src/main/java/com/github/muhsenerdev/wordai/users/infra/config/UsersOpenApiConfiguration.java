package com.github.muhsenerdev.wordai.users.infra.config;

import io.swagger.v3.oas.models.examples.Example;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersOpenApiConfiguration {

    public static final String EXAMPLE_USERNAME_CONFLICT = "UsernameConflict";

    @Bean
    public OpenApiCustomizer usersOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null) {
                openApi.setComponents(new io.swagger.v3.oas.models.Components());
            }

            openApi.getComponents().addExamples(EXAMPLE_USERNAME_CONFLICT,
                    new Example().summary("Username already exists")
                            .description("The provided username is already taken by another user.").value("""
                                    {
                                      "status": 409,
                                      "path": "/api/v1/onboarding",
                                      "timestamp": "2023-10-27T10:00:00Z",
                                      "resource": "User",
                                      "field": "username",
                                      "value": "johndoe"
                                    }
                                    """));
        };
    }
}

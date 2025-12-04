package com.github.muhsenerdev.commons.core.config;

import com.github.muhsenerdev.commons.core.response.*;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonsOpenApiConfiguration {

    @Bean
    public OpenApiCustomizer commonsOpenApiCustomizer() {
        return openApi -> {
            Components components = openApi.getComponents();
            if (components == null) {
                components = new Components();
                openApi.setComponents(components);
            }

            registerSchema(components, BadRequestResponse.class);
            registerSchema(components, NotFoundResponse.class);
            registerSchema(components, ConflictResponse.class);
            registerSchema(components, InternalResponse.class);
            registerSchema(components, UnauthorizedResponse.class);
            registerSchema(components, ForbiddenResponse.class);
        };
    }

    private void registerSchema(Components components, Class<?> clazz) {
        var resolvedSchema = ModelConverters.getInstance().readAllAsResolvedSchema(clazz);
        if (resolvedSchema != null && resolvedSchema.schema != null) {
            components.addSchemas(clazz.getSimpleName(), resolvedSchema.schema);
            // Also add referenced schemas (e.g. if BadRequestResponse uses other objects)
            if (resolvedSchema.referencedSchemas != null) {
                resolvedSchema.referencedSchemas.forEach(components::addSchemas);
            }
        }
    }
}

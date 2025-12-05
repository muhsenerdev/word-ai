package com.github.muhsenerdev.genai.domain.prompt;

import java.util.Map;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Type;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "prompt_definitions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE prompt_definitions SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? ")
@SQLRestriction("deleted_at IS NULL")
@Getter
public class PromptDefinition extends SoftDeletableEntity<PromptId> {

        @EmbeddedId
        @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "id", nullable = false)))
        private PromptId id;

        @Column(name = "name")
        private String name;

        @Embedded
        @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "slug", nullable = false)))
        private Slug slug;

        @Column(name = "provider", nullable = false)
        @Enumerated(EnumType.STRING)
        private LLMProvider provider;

        @Column(name = "model", nullable = false)
        private String model;

        @Column(name = "system_message")
        private String systemMessage;

        @Column(name = "user_message_template", nullable = false)
        private String userMessageTemplate;

        @Embedded
        @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "input_schema", nullable = true, columnDefinition = "jsonb")))
        private PayloadSchema inputSchema;

        @Embedded
        @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "output_schema", nullable = true, columnDefinition = "jsonb")))
        private PayloadSchema outputSchema;

        @Enumerated(EnumType.STRING)
        @Column(name = "output_type", nullable = false)
        private PromptOutputType outputType;

        @Column(name = "model_options", columnDefinition = "jsonb")
        @Type(JsonType.class)
        private Map<String, Object> modelOptions;

        protected PromptDefinition(PromptId id, String name, Slug slug, LLMProvider provider, String model,
                        String systemMessage, String userMessageTemplate, PayloadSchema inputSchema,
                        PayloadSchema outputSchema, Map<String, Object> modelOptions, PromptOutputType outputType) {
                this.id = id;
                this.name = name;
                this.slug = slug;
                this.provider = provider;
                this.model = model;
                this.systemMessage = systemMessage;
                this.userMessageTemplate = userMessageTemplate;
                this.inputSchema = inputSchema;
                this.outputSchema = outputSchema;
                this.modelOptions = modelOptions;
                this.outputType = outputType;

                DomainUtils.notNull(id, "Prompt id is required.", "prompt.id.required");
                DomainUtils.notNull(slug, "Prompt slug is required.", "prompt.slug.required");
                DomainUtils.notNull(outputType, "prompt.output-type.required");
                DomainUtils.notNull(provider, "prompt.provider.required");
                DomainUtils.notNull(model, "prompt.model.required");
                DomainUtils.notNull(userMessageTemplate, "prompt.user-message-template.required");

        }

        @Builder(access = AccessLevel.PROTECTED)
        private static PromptDefinition create(String name, Slug slug, LLMProvider provider, String model,
                        String systemMessage, String userMessageTemplate, PayloadSchema inputSchema,
                        PayloadSchema outputSchema, Map<String, Object> modelOptions, PromptOutputType outputType) {

                PromptDefinition def = new PromptDefinition(PromptId.random(), name, slug, provider, model,
                                systemMessage, userMessageTemplate, inputSchema, outputSchema, modelOptions,
                                outputType);

                return def;
        }

        @Override
        public PromptId getId() {
                return id;
        }

}

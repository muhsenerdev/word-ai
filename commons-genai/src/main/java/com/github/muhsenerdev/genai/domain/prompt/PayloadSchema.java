package com.github.muhsenerdev.genai.domain.prompt;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.core.InvalidDomainObjectException;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@EqualsAndHashCode
public class PayloadSchema {

	@Column(name = "value", columnDefinition = "jsonb")
	@Type(JsonType.class)
	@JdbcTypeCode(SqlTypes.JSON)
	private JsonNode value;

	// It trusts PayloadSchemaFactory for deep validation.
	protected PayloadSchema(JsonNode value) throws InvalidDomainObjectException {
		DomainUtils.notNull(value, "Value of Paylaod Schema cannot be null");
		this.value = value;
	}

	protected PayloadSchema of(JsonNode node) {
		return new PayloadSchema(node);
	}

}

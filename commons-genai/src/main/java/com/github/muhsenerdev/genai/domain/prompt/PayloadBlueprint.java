package com.github.muhsenerdev.genai.domain.prompt;

import com.github.muhsenerdev.commons.core.Assert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@EqualsAndHashCode
public class PayloadBlueprint {

    private String rawSchemaContent;

    protected PayloadBlueprint(String rawSchemaContent) {
        Assert.hasText(rawSchemaContent, "rawSchemaContent cannot be null or blank.");
        this.rawSchemaContent = rawSchemaContent;
    }

}

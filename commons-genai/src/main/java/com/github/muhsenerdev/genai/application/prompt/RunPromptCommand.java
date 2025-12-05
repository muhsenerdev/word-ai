package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.jpa.Slug;
import lombok.Builder;

import java.util.Map;

public record RunPromptCommand(Slug slug, Map<String, Object> input) {

    @Builder(toBuilder = true)
    public RunPromptCommand(Slug slug, Map<String, Object> input) {
        this.slug = slug;
        this.input = input;

        Assert.notNull(slug, "Slug cannot be null!");
    }
}

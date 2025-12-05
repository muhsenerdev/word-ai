package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.jpa.*;
import com.github.muhsenerdev.genai.domain.prompt.PromptId;

import lombok.Builder;

public record PromptDefCreationResponse(PromptId id, Slug slug) {

    @Builder(toBuilder = true)
    public PromptDefCreationResponse(PromptId id, Slug slug) {
        this.id = id;
        this.slug = slug;
    }

}

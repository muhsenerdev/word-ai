package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.jpa.Slug;
import com.github.muhsenerdev.commons.core.exception.ResourceNotFoundException;

public class PromptNotFoundException extends ResourceNotFoundException {

    public PromptNotFoundException(Slug slug) {
        super("prompt-definition", "slug", slug);
    }
}

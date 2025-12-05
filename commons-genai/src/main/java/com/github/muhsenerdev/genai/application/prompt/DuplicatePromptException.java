package com.github.muhsenerdev.genai.application.prompt;

import com.github.muhsenerdev.commons.core.exception.DuplicateResourceException;
import com.github.muhsenerdev.commons.jpa.Slug;

public class DuplicatePromptException extends DuplicateResourceException {

	public DuplicatePromptException(Slug slug) {
		super("prompt", "slug", slug.getValue(), "prompt.duplicated.slug");
	}

	public DuplicatePromptException(Slug slug, String code) {
		super("prompt", "slug", slug.getValue(), code);
	}
}

package com.github.muhsenerdev.genai.application.prompt;

public class PromptExecutionException extends RuntimeException {

    public PromptExecutionException(String message) {
        super(message);
    }

    public PromptExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

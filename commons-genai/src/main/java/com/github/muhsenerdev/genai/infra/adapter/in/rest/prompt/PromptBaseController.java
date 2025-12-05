package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.muhsenerdev.genai.application.prompt.CreatePromptCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptApplicationService;
import com.github.muhsenerdev.genai.application.prompt.PromptCreationResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Prompts", description = "Prompts Management")
public abstract class PromptBaseController {

    private final PromptWebMapper mapper;
    private final PromptApplicationService service;

    @PostMapping
    @CreatePromptDocs
    public ResponseEntity<PromptCreationOutput> createPrompt(@Valid @RequestBody CreatePromptInput request) {
        CreatePromptCommand command = mapper.toCreateCommand(request);
        PromptCreationResponse response = service.create(command);
        PromptCreationOutput body = mapper.toCreationOutput(response);
        return ResponseEntity.ok(body);
    }

}

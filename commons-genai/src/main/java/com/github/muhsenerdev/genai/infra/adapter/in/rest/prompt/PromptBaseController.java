package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import com.github.muhsenerdev.genai.application.prompt.CreatePromptDefCommand;
import com.github.muhsenerdev.genai.application.prompt.PromptApplicationService;
import com.github.muhsenerdev.genai.application.prompt.PromptDefCreationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
public abstract class PromptBaseController {

    private final PromptRestMapper mapper;
    private final PromptApplicationService service;

    @PostMapping
    public ResponseEntity<PromptCreationRestResponse> createPrompt(@RequestBody CreatePromptRequest request) {
        CreatePromptDefCommand command = mapper.toCreateCommand(request);
        PromptDefCreationResponse response = service.create(command);

        PromptCreationRestResponse body = mapper.toRestResponse(response);

        return ResponseEntity.ok(body);
    }

}

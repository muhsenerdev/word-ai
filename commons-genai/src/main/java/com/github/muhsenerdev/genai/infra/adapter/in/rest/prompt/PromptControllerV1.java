package com.github.muhsenerdev.genai.infra.adapter.in.rest.prompt;

import com.github.muhsenerdev.genai.application.prompt.PromptApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prompts")
public class PromptControllerV1 extends PromptBaseController {

    public PromptControllerV1(PromptRestMapper mapper, PromptApplicationService service) {
        super(mapper, service);
    }
}

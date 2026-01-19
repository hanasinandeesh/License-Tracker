package com.prodapt.license_tracker.ai.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.ai.dto.AiQueryRequest;
import com.prodapt.license_tracker.ai.dto.AiQueryResponse;
import com.prodapt.license_tracker.ai.service.AiService;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/query")
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR','ENGINEER')")
    public AiQueryResponse query(@RequestBody AiQueryRequest req) {
        return new AiQueryResponse(
                aiService.handleQuery(req.getQuestion())
        );
    }
}

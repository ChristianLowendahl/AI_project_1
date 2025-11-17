package com.example.aiproject1.summary;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summarize")
@CrossOrigin(origins = "http://localhost:4200")
public class SummaryController {

    private final OllamaService ollamaService;

    public SummaryController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping
    public SummaryResponse summarize(@RequestBody SummaryRequest request) {
        String result = ollamaService.summarize(request.getText());
        return new SummaryResponse(result);
    }

}

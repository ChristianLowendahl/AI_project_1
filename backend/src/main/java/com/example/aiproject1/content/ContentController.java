package com.example.aiproject1.content;

import com.example.aiproject1.ollama.OllamaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins = "http://localhost:4200")
public class ContentController {

    private final OllamaService ollamaService;

    public ContentController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping
    public ContentResponse generateContent(@RequestBody ContentRequest request) {
        String result = ollamaService.generateContent(request.getText());
        return new ContentResponse(result);
    }

}


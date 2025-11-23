package com.example.aiproject1.questions;

import com.example.aiproject1.ollama.OllamaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionsController {

    private final OllamaService ollamaService;

    public QuestionsController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping
    public QuestionsResponse generateQuestions(@RequestBody QuestionsRequest request) {
        String result = ollamaService.generateQuestions(request.getText());
        return new QuestionsResponse(result);
    }

}

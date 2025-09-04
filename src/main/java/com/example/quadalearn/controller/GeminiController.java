package com.example.quadalearn.controller;

import com.example.quadalearn.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/ask")
    public String askGemini(@RequestParam String prompt) {
        return geminiService.generateText(prompt);
    }
}

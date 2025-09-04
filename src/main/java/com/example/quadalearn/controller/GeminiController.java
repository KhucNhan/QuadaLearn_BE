package com.example.quadalearn.controller;

import com.example.quadalearn.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class GeminiController  {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> askAI(@RequestBody Map<String, String> input) {
        String prompt = input.get("prompt");

        try {
            String response = geminiService.askAI(prompt);
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Lỗi khi gọi AI",
                    "message", e.getMessage()
            ));
        }
    }
}

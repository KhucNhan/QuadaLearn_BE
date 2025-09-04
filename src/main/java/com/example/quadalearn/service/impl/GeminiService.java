package com.example.quadalearn.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";

    public String generateText(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(GEMINI_URL, apiKey);

        // Request body (the format Gemini API expects)
        Map<String, Object> requestBody = Map.of(
                "contents", Collections.singletonList(
                        Map.of("parts", Collections.singletonList(
                                Map.of("text", prompt)
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            try {
                // Lấy text trong response
                Map candidate = (Map) ((java.util.List) response.getBody().get("candidates")).get(0);
                Map content = (Map) candidate.get("content");
                java.util.List parts = (java.util.List) content.get("parts");
                Map firstPart = (Map) parts.get(0);
                return (String) firstPart.get("text");
            } catch (Exception e) {
                return "Không đọc được response từ Gemini: " + e.getMessage();
            }
        }
        return "Lỗi gọi API Gemini: " + response.getStatusCode();
    }
}

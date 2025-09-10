package com.example.quadalearn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    public String askAI(String prompt) throws Exception {
        // --- JSON request
        String jsonInput = "{"
                + "\"contents\": [{\"parts\":[{\"text\":\"" + prompt.replace("\"", "\\\"") + "\"}]}]"
                + "}";
        System.out.println("👉 JSON body: " + jsonInput);

        // --- Gọi API
        URL url = new URL(GEMINI_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("X-goog-api-key", geminiApiKey);
        conn.setDoOutput(true);

        // In ra headers
        System.out.println("👉 Headers:");
        conn.getRequestProperties().forEach((k,v) -> System.out.println("   " + k + ": " + v));

        // Ghi body
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        int statusCode = conn.getResponseCode();
        System.out.println("👉 HTTP Status: " + statusCode);

        InputStream is = (statusCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
        String response = "";
        if (is != null) {
            response = new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
        }

        System.out.println("👉 Raw response: " + response);

        if (statusCode >= 400) {
            throw new RuntimeException("Gemini API error: " + statusCode + " - " + response);
        }

        // --- Parse response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        if (root.has("candidates") && root.get("candidates").isArray() && root.get("candidates").size() > 0) {
            String text = root.get("candidates").get(0)
                    .path("content").path("parts").get(0).path("text").asText().trim();
            System.out.println("👉 Parsed text: " + text);
            return text;
        }

        return "AI không trả lời được";
    }

    public String analyzeTest(String aim, List<Map<String, String>> answers, double score) throws Exception {
        StringBuilder answersText = new StringBuilder();
        for (Map<String, String> ans : answers) {
            answersText.append("Q: ").append(ans.get("question")).append("\n")
                    .append("Correct: ").append(ans.get("correct_answer")).append("\n")
                    .append("User: ").append(ans.get("user_answer")).append("\n")
                    .append("Tag: ").append(ans.get("knowledgeTag")).append("\n\n");
        }

        String prompt =
                "Bạn là 1 giáo viên tiếng Anh. Hãy phân tích bài làm gồm 50 câu của học sinh dựa trên CEFR.\n\n" +
                        "Mục tiêu: " + aim + "\n" +
                        "Điểm: " + String.format("%.2f", score) + "\n" +
                        "Các câu trả lời:\n" + answersText +
                        "Công việc của bạn:\n" +
                        "1. Tổng hợp % đúng và ước tính CEFR. \n" +
                        "2. Xác định các chủ đề ngữ pháp mà người dùng mạnh và yếu. \n" +
                        "3. Cung cấp phản hồi được cá nhân hóa. \n" +
                        "4. Đề xuất lộ trình để đạt mục tiêu mục tiêu (" + aim + ").";


        return askAI(prompt); // gọi API Gemini như bạn viết trước đó
    }
}

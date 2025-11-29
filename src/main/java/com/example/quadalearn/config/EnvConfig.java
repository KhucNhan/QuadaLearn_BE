package com.example.quadalearn.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class EnvConfig {
    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
        String googleClientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");

        // Thêm kiểm tra null
        if (googleClientId != null) {
            System.setProperty("GOOGLE_CLIENT_ID", googleClientId);
        } else {
            System.err.println("❌ Cảnh báo: GOOGLE_CLIENT_ID bị thiếu. Kiểm tra file .env.");
        }

        if (googleClientSecret != null) {
            System.setProperty("GOOGLE_CLIENT_SECRET", googleClientSecret);
        } else {
            System.err.println("❌ Cảnh báo: GOOGLE_CLIENT_SECRET bị thiếu. Kiểm tra file .env.");
        }
    }
}
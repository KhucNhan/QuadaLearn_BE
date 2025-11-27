package com.example.quadalearn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling // ✅ Enable scheduled tasks
public class SchedulingConfig {
    // Configuration class để enable @Scheduled
}
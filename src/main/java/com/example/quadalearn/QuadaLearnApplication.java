package com.example.quadalearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.quadalearn.repository")
//@EntityScan(basePackages = "com.example.quadalearn.model")
public class QuadaLearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuadaLearnApplication.class, args);
    }
}

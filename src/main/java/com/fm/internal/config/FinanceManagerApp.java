package com.fm.internal.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.fm.internal")
@EntityScan("com.fm.internal.models")
@EnableScheduling
public class FinanceManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(FinanceManagerApp.class, args);
    }
}

package com.fm.internal.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.fm.internal")
@EntityScan("com.fm.internal.models")
@EnableJpaRepositories("com.fm.internal.repository")
public class FinanceManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(FinanceManagerApp.class, args);
    }
}

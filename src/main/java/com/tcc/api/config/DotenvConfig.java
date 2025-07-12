package com.tcc.api.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("API_NAME", dotenv.get("API_NAME"));
        System.setProperty("API_VERSION", dotenv.get("API_VERSION"));
        System.setProperty("API_MAIL_HOST", dotenv.get("API_MAIL_HOST"));
        System.setProperty("API_MAIL_PORT", dotenv.get("API_MAIL_PORT"));
        System.setProperty("API_MAIL_USERNAME", dotenv.get("API_MAIL_USERNAME"));
        System.setProperty("API_MAIL_PASSWORD", dotenv.get("API_MAIL_PASSWORD"));
        System.setProperty("API_SECRET_KEY", dotenv.get("API_SECRET_KEY"));
        System.setProperty("API_FROM_EMAIL", dotenv.get("API_FROM_EMAIL"));
    }
}
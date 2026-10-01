package com.foodtruth.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Provides the RestClient bean used by the service layer to call external APIs.
 * Spring Boot 3.2+ ships RestClient — no additional dependency needed.
 */
@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .build();
    }
}

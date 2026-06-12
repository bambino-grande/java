package ru.pogosian.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class StorageClientConfig {
    @Bean
    public RestClient storageRestClient(@Value("${storage-service.base-url:http://localhost:8082}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}

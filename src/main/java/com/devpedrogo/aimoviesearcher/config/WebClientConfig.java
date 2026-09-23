package com.devpedrogo.aimoviesearcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    @Value("${tmdb.api.token}")
    private String bearerToken;

    @Bean
    public WebClient tmdbWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }
}
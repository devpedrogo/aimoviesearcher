package com.devpedrogo.aimoviesearcher.service;

import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TmdbService {

    private final WebClient tmdbWebClient;

    public TmdbService(WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    public TmdbSearchResponseDto searchMulti(String query, String year, String language) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParamIfPresent("year", java.util.Optional.ofNullable(year))
                        .queryParam("language", language != null ? language : "pt-BR")
                        .queryParam("include_adult", false)
                        .build())
                .retrieve()
                .bodyToMono(TmdbSearchResponseDto.class)
                .block(); // Chamada síncrona para simplificar o Controller REST
    }
}
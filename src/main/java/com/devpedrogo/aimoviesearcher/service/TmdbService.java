package com.devpedrogo.aimoviesearcher.service;

import com.devpedrogo.aimoviesearcher.dto.TmdbMediaDto;
import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TmdbService {

    private final WebClient tmdbWebClient;

    public TmdbService(WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    public TmdbSearchResponseDto searchMulti(String query, String year, String language) {
        TmdbSearchResponseDto rawResponse = tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParam("language", language != null ? language : "pt-BR")
                        .queryParam("include_adult", false)
                        .build())
                .retrieve()
                .bodyToMono(TmdbSearchResponseDto.class)
                .block();

        // Se o usuário especificou um ano, filtramos a lista em memória
        if (year != null && !year.isBlank() && rawResponse != null && rawResponse.results() != null) {
            List<TmdbMediaDto> filteredResults = rawResponse.results().stream()
                    .filter(media -> {
                        String date = media.getDisplayDate();
                        return date != null && date.startsWith(year); // Compara o início do YYYY-MM-DD
                    })
                    .toList();

            return new TmdbSearchResponseDto(
                    rawResponse.page(),
                    filteredResults,
                    rawResponse.totalPages(),
                    filteredResults.size()
            );
        }

        return rawResponse;
    }
}
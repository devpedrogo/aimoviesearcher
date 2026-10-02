package com.devpedrogo.aimoviesearcher.service;

import com.devpedrogo.aimoviesearcher.dto.TmdbMediaDto;
import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import com.devpedrogo.aimoviesearcher.exception.TmdbApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Service
public class TmdbService {

    private final WebClient tmdbWebClient;

    public TmdbService(WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    public TmdbSearchResponseDto searchMulti(String query, String year, String language) {
        try {
            TmdbSearchResponseDto rawResponse = tmdbWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/multi")
                            .queryParam("query", query)
                            .queryParam("language", language != null ? language : "pt-BR")
                            .queryParam("include_adult", false)
                            .build())
                    .retrieve()
                    .bodyToMono(TmdbSearchResponseDto.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();

            if (year != null && !year.isBlank() && rawResponse != null && rawResponse.results() != null) {
                List<TmdbMediaDto> filteredResults = rawResponse.results().stream()
                        .filter(media -> {
                            String date = media.getDisplayDate();
                            return date != null && date.startsWith(year);
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
        } catch (Exception e) {
            throw new TmdbApiException("Falha na consulta de mídia na API do TMDB.", e);
        }
    }

    public List<TmdbMediaDto> getRecommendations(Long id, String mediaType) {
        try {
            String path = "movie".equalsIgnoreCase(mediaType) 
                    ? "/movie/" + id + "/recommendations" 
                    : "/tv/" + id + "/recommendations";

            TmdbSearchResponseDto response = tmdbWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(path)
                            .queryParam("language", "pt-BR")
                            .build())
                    .retrieve()
                    .bodyToMono(TmdbSearchResponseDto.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            return response != null && response.results() != null ? response.results() : List.of();
        } catch (Exception e) {
            // Se falhar a recomendação, retorna lista vazia sem derrubar a busca principal
            return List.of();
        }
    }
}
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

    /**
     * Inicializa o serviço responsável pelas consultas à API do TMDB.
     *
     * @param tmdbWebClient cliente HTTP configurado para acessar o TMDB
     */
    public TmdbService(WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    /**
     * Busca filmes e séries pelo título ou termo informado.
     *
     * @param query texto utilizado na consulta ao TMDB
     * @param year ano opcional usado para filtrar os resultados pela data de lançamento
     * @param language idioma da resposta; quando nulo, utiliza o idioma português brasileiro
     * @return resposta da busca contendo os resultados encontrados
     * @throws TmdbApiException quando ocorre uma falha durante a consulta ao TMDB
     */
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

    /**
     * Obtém mídias recomendadas com base em um filme ou série existente.
     *
     * @param id identificador da mídia no TMDB
     * @param mediaType tipo da mídia; "movie" identifica filmes e os demais valores identificam séries
     * @return lista de recomendações ou uma lista vazia quando a consulta falhar
     */
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
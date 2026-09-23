package com.devpedrogo.aimoviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMediaDto(
    Long id,
    String title,
    String name, // O TMDB usa 'name' para séries e 'title' para filmes
    
    @JsonProperty("original_title")
    String originalTitle,
    
    @JsonProperty("media_type")
    String mediaType, // 'movie' ou 'tv'
    
    String overview,
    
    @JsonProperty("poster_path")
    String posterPath,
    
    @JsonProperty("release_date")
    String releaseDate, // Filmes
    
    @JsonProperty("first_air_date")
    String firstAirDate, // Séries
    
    @JsonProperty("vote_average")
    Double voteAverage
) {
    // Método utilitário para unificar o título (filme vs série)
    public String getDisplayTitle() {
        return title != null ? title : name;
    }

    // Método utilitário para unificar a data de lançamento
    public String getDisplayDate() {
        return releaseDate != null ? releaseDate : firstAirDate;
    }
}
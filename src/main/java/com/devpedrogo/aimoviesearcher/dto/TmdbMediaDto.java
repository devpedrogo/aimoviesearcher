package com.devpedrogo.aimoviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filme ou série retornado pelo TMDB")
public record TmdbMediaDto(
    @Schema(description = "Identificador no TMDB", example = "157336")
    Long id,
    @Schema(description = "Título do filme")
    String title,
    @Schema(description = "Nome da série")
    String name, // O TMDB usa 'name' para séries e 'title' para filmes
    
    @JsonProperty("original_title")
    @Schema(description = "Título original")
    String originalTitle,
    
    @JsonProperty("media_type")
    @Schema(description = "Tipo de mídia", example = "movie")
    String mediaType, // 'movie' ou 'tv'
    
    @Schema(description = "Sinopse")
    String overview,
    
    @JsonProperty("poster_path")
    @Schema(description = "Caminho do pôster no TMDB")
    String posterPath,
    
    @JsonProperty("release_date")
    @Schema(description = "Data de lançamento do filme")
    String releaseDate, // Filmes
    
    @JsonProperty("first_air_date")
    @Schema(description = "Data de estreia da série")
    String firstAirDate, // Séries
    
    @JsonProperty("vote_average")
    @Schema(description = "Nota média", example = "8.4")
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
package com.devpedrogo.aimoviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Paginação e resultados da pesquisa")
public record TmdbSearchResponseDto(
    @Schema(description = "Página retornada", example = "1")
    int page,
    @Schema(description = "Filmes e séries encontrados")
    List<TmdbMediaDto> results,
    
    @JsonProperty("total_pages")
    @Schema(description = "Total de páginas", example = "5")
    int totalPages,
    
    @JsonProperty("total_results")
    @Schema(description = "Total de resultados", example = "42")
    int totalResults
) {}
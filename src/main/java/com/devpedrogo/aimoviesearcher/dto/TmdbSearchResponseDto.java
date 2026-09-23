package com.devpedrogo.aimoviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TmdbSearchResponseDto(
    int page,
    List<TmdbMediaDto> results,
    
    @JsonProperty("total_pages")
    int totalPages,
    
    @JsonProperty("total_results")
    int totalResults
) {}
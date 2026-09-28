package com.devpedrogo.aimoviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record SearchParameters(
    @JsonPropertyDescription("O nome exato ou termo simplificado do filme/série inferido a partir da busca do usuário. Exemplo: se o usuário pedir 'filme de ficção do Nolan com sonhos', inferir 'Inception'.")
    String query,

    @JsonPropertyDescription("O ano de lançamento (YYYY) se mencionado ou inferido. Deixar nulo se não houver um ano claro.")
    String year,

    @JsonPropertyDescription("O tipo de mídia desejada: 'movie' para filmes, 'tv' para séries ou 'multi' se indeterminado.")
    String mediaType
) {}
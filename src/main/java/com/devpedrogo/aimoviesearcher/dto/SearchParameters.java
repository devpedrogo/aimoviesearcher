package com.devpedrogo.aimoviesearcher.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record SearchParameters(
    @JsonPropertyDescription("Se a solicitação se referir a um filme/série exato e famoso, retorne o título. Caso seja a descrição de uma premissa/enredo, retorne as principais palavras-chave em inglês e português separadas por espaço (Ex: 'wish obsessed girlfriend horror').")    
    String query,

    @JsonPropertyDescription("O ano de lançamento (YYYY) se mencionado ou inferido. Deixar nulo se não houver um ano claro.")
    String year,

    @JsonPropertyDescription("O tipo de mídia desejada: 'movie' para filmes, 'tv' para séries ou 'multi' se indeterminado.")
    String mediaType
) {}
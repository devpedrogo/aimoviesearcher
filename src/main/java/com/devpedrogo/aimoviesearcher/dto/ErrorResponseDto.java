package com.devpedrogo.aimoviesearcher.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes de uma resposta de erro")
public record ErrorResponseDto(
    @Schema(description = "Código HTTP", example = "502")
    int status,
    @Schema(description = "Descrição do erro", example = "Erro de Comunicação com TMDB")
    String error,
    @Schema(description = "Detalhe do erro")
    String message,
    @Schema(description = "Data e hora do erro")
    LocalDateTime timestamp
) {
    public ErrorResponseDto(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now());
    }
}
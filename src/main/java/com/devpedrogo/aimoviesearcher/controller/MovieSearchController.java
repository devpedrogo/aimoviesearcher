package com.devpedrogo.aimoviesearcher.controller;

import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import com.devpedrogo.aimoviesearcher.dto.ErrorResponseDto;
import com.devpedrogo.aimoviesearcher.service.MovieOrchestratorService;
import com.devpedrogo.aimoviesearcher.service.TmdbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@Tag(name = "Busca", description = "Pesquisa de filmes e séries")
public class MovieSearchController {

    private final TmdbService tmdbService;
    private final MovieOrchestratorService movieOrchestratorService;

    public MovieSearchController(TmdbService tmdbService, MovieOrchestratorService movieOrchestratorService) {
        this.tmdbService = tmdbService;
        this.movieOrchestratorService = movieOrchestratorService;
    }

    @GetMapping("/normal")
    @Operation(summary = "Busca por texto", description = "Pesquisa filmes e séries no TMDB por texto e, opcionalmente, ano.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados encontrados"),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido ou ausente"),
            @ApiResponse(responseCode = "502", description = "Falha na comunicação com o TMDB",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<TmdbSearchResponseDto> searchNormal(
            @Parameter(description = "Título ou termos de busca", example = "Interestelar")
            @RequestParam String query,
            @Parameter(description = "Ano de lançamento", example = "2014")
            @RequestParam(required = false) String year) {
        
        return ResponseEntity.ok(tmdbService.searchMulti(query, year, "pt-BR"));
    }

    @GetMapping("/ai")
    @Operation(summary = "Busca com IA", description = "Interpreta a descrição e busca títulos correspondentes; se a IA falhar, pesquisa o texto diretamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados encontrados"),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido ou ausente"),
            @ApiResponse(responseCode = "502", description = "Falha na comunicação com o TMDB",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<TmdbSearchResponseDto> searchWithAi(
            @Parameter(description = "Descrição do filme ou série desejado", example = "filme sobre sonhos dentro de sonhos")
            @RequestParam String prompt) {
        return ResponseEntity.ok(movieOrchestratorService.searchWithAi(prompt));
    }
}
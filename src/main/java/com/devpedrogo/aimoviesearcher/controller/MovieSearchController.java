package com.devpedrogo.aimoviesearcher.controller;

import com.devpedrogo.aimoviesearcher.dto.SearchParameters;
import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import com.devpedrogo.aimoviesearcher.service.AiSearchService;
import com.devpedrogo.aimoviesearcher.service.TmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*") // Liberado para desenvolvimento local com Angular
public class MovieSearchController {

    private final TmdbService tmdbService;
    private final AiSearchService aiSearchService;

    public MovieSearchController(TmdbService tmdbService, AiSearchService aiSearchService) {
        this.tmdbService = tmdbService;
        this.aiSearchService = aiSearchService;
    }

    @GetMapping("/normal")
    public ResponseEntity<TmdbSearchResponseDto> searchNormal(
            @RequestParam String query,
            @RequestParam(required = false) String year) {
        
        TmdbSearchResponseDto result = tmdbService.searchMulti(query, year, "pt-BR");
        return ResponseEntity.ok(result);
    }

    // Busca Inteligente (Assistida por IA)
    @GetMapping("/ai")
    public ResponseEntity<TmdbSearchResponseDto> searchWithAi(@RequestParam String prompt) {
        // 1. A IA extrai a intenção do usuário no record Java
        SearchParameters params = aiSearchService.parseUserPrompt(prompt);

        // 2. O TMDB é consultado com o termo limpo e o ano extraídos
        TmdbSearchResponseDto result = tmdbService.searchMulti(
                params.query(), 
                params.year(), 
                "pt-BR"
        );

        return ResponseEntity.ok(result);
    }
}
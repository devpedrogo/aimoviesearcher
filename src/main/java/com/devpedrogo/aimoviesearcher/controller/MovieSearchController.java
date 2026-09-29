package com.devpedrogo.aimoviesearcher.controller;

import com.devpedrogo.aimoviesearcher.dto.SearchParameters;
import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import com.devpedrogo.aimoviesearcher.service.AiSearchService;
import com.devpedrogo.aimoviesearcher.service.TmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
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
        try {
            SearchParameters params = aiSearchService.parseUserPrompt(prompt);
            TmdbSearchResponseDto result = tmdbService.searchMulti(
                    params.query(), 
                    params.year(), 
                    "pt-BR"
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("Fallback acionado devido a falha na IA: " + e.getMessage());
            TmdbSearchResponseDto fallbackResult = tmdbService.searchMulti(prompt, null, "pt-BR");
            return ResponseEntity.ok(fallbackResult);
        }
    }
}
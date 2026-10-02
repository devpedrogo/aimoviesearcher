package com.devpedrogo.aimoviesearcher.controller;

import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import com.devpedrogo.aimoviesearcher.service.MovieOrchestratorService;
import com.devpedrogo.aimoviesearcher.service.TmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class MovieSearchController {

    private final TmdbService tmdbService;
    private final MovieOrchestratorService movieOrchestratorService;

    public MovieSearchController(TmdbService tmdbService, MovieOrchestratorService movieOrchestratorService) {
        this.tmdbService = tmdbService;
        this.movieOrchestratorService = movieOrchestratorService;
    }

    @GetMapping("/normal")
    public ResponseEntity<TmdbSearchResponseDto> searchNormal(
            @RequestParam String query,
            @RequestParam(required = false) String year) {
        
        return ResponseEntity.ok(tmdbService.searchMulti(query, year, "pt-BR"));
    }

    @GetMapping("/ai")
    public ResponseEntity<TmdbSearchResponseDto> searchWithAi(@RequestParam String prompt) {
        return ResponseEntity.ok(movieOrchestratorService.searchWithAi(prompt));
    }
}
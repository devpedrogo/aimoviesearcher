package com.devpedrogo.aimoviesearcher.controller;

import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import com.devpedrogo.aimoviesearcher.service.TmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*") // Liberado para desenvolvimento local com Angular
public class MovieSearchController {

    private final TmdbService tmdbService;

    public MovieSearchController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/normal")
    public ResponseEntity<TmdbSearchResponseDto> searchNormal(
            @RequestParam String query,
            @RequestParam(required = false) String year) {
        
        TmdbSearchResponseDto result = tmdbService.searchMulti(query, year, "pt-BR");
        return ResponseEntity.ok(result);
    }
}
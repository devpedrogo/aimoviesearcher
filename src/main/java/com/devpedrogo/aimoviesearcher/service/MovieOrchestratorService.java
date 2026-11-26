package com.devpedrogo.aimoviesearcher.service;

import com.devpedrogo.aimoviesearcher.dto.SearchParameters;
import com.devpedrogo.aimoviesearcher.dto.TmdbMediaDto;
import com.devpedrogo.aimoviesearcher.dto.TmdbSearchResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieOrchestratorService {

    private final AiSearchService aiSearchService;
    private final TmdbService tmdbService;

    /**
     * Inicializa o serviço que coordena a interpretação por IA e as consultas ao TMDB.
     *
     * @param aiSearchService serviço usado para interpretar a solicitação do usuário
     * @param tmdbService serviço usado para buscar mídias e recomendações
     */
    public MovieOrchestratorService(AiSearchService aiSearchService, TmdbService tmdbService) {
        this.aiSearchService = aiSearchService;
        this.tmdbService = tmdbService;
    }

    /**
     * Interpreta o texto do usuário e realiza uma busca enriquecida com recomendações.
     *
     * <p>Primeiro, a solicitação é convertida em parâmetros pela IA. Em seguida, o TMDB é consultado
     * e os principais resultados são complementados com recomendações relacionadas. Se a IA falhar,
     * a busca original é enviada diretamente ao TMDB como alternativa.</p>
     *
     * @param prompt descrição ou termo de pesquisa informado pelo usuário
     * @return resultados principais e recomendações em uma única resposta de busca
     */
    public TmdbSearchResponseDto searchWithAi(String prompt) {
        try {
            // 1. A IA extrai os parâmetros da frase do usuário
            SearchParameters params = aiSearchService.parseUserPrompt(prompt);

            // 2. Consulta os resultados diretos no TMDB
            TmdbSearchResponseDto primaryResult = tmdbService.searchMulti(params.query(), params.year(), "pt-BR");

            if (primaryResult == null || primaryResult.results() == null || primaryResult.results().isEmpty()) {
                return primaryResult;
            }

            // 3. Garante ordenação mantendo unicidade dos resultados
            Set<TmdbMediaDto> combinedResults = new LinkedHashSet<>(primaryResult.results());

            // 4. Complementa com recomendações da mídia principal (se aplicável)
            TmdbMediaDto topMatch = primaryResult.results().get(0);
            String type = topMatch.mediaType() != null ? topMatch.mediaType() : params.mediaType();
            
            List<TmdbMediaDto> recommendations = tmdbService.getRecommendations(topMatch.id(), type);
            if (recommendations != null) {
                combinedResults.addAll(recommendations);
            }

            List<TmdbMediaDto> finalList = new ArrayList<>(combinedResults);

            return new TmdbSearchResponseDto(
                    primaryResult.page(),
                    finalList,
                    primaryResult.totalPages(),
                    finalList.size()
            );

        } catch (Exception e) {
            // Fallback direto no TMDB caso ocorra falha na IA
            return tmdbService.searchMulti(prompt, null, "pt-BR");
        }
    }
}
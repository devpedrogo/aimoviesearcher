package com.devpedrogo.aimoviesearcher.service;

import com.devpedrogo.aimoviesearcher.dto.SearchParameters;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiSearchService {

    private final ChatClient chatClient;

    public AiSearchService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public SearchParameters parseUserPrompt(String userPrompt) {
        String systemPrompt = """
            Você é um assistente especialista em cinema e TV responsável por extrair parâmetros de busca para o TMDB.
            Analise a solicitação do usuário e infira o título mais provável, ano e tipo de mídia.
            Responda ESTRITAMENTE no formato do objeto solicitado.
            """;

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .entity(SearchParameters.class);
    }
}
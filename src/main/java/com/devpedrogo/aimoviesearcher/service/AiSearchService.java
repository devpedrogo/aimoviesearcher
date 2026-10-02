package com.devpedrogo.aimoviesearcher.service;

import com.devpedrogo.aimoviesearcher.dto.SearchParameters;
import com.devpedrogo.aimoviesearcher.exception.AiProcessingException;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiSearchService {

    private final ChatClient chatClient;

    public AiSearchService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public SearchParameters parseUserPrompt(String userPrompt) {
        try {
            String systemPrompt = """
                Você é um especialista e enciclopédia em cinema e TV.
                Sua missão é identificar o filme ou série exato correspondente à descrição do usuário para ser buscado no TMDB.
                
                REGRAS OBRIGATÓRIAS PARA O CAMPO 'query':
                1. Se o usuário fornecer uma sinopse, trama ou enredo (ex: 'filme de namorada obcecada após desejo do amigo se tornar realidade terror'), IDENTIFIQUE o título real do filme mais provável (ex: 'Wishcraft' ou 'Desejo Sombrio' ou 'Devil in the Flesh').
                2. NUNCA retorne frases genéricas, lista de palavras-chave em inglês ou descrições no campo 'query'. Retorne APENAS o título comercial do filme ou série.
                3. Se não tiver certeza absoluta de um único filme, retorne o título da obra mais famosa que melhor represente esse enredo.
                
                Responda ESTRITAMENTE no formato do objeto solicitado.
                """;

            return chatClient.prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .call()
                    .entity(SearchParameters.class);
        } catch (Exception e) {
            throw new AiProcessingException("Não foi possível interpretar a solicitação com a IA.", e);
        }
    }
}
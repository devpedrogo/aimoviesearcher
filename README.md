# 🎬 AI Movie Searcher

O **AI Movie Searcher** é uma aplicação web moderna (*Spring Boot 3 + Angular*) que utiliza a **IA do Google Gemini (3.8-Flash)** e a API do **TMDB** para interpretar sinopses, enredos e pesquisas em linguagem natural, exibindo os resultados em uma interface escura e minimalista inspirada no estilo do Gemini.

---

## 🚀 Tecnologias Utilizadas

### Backend
* **Java 17+** & **Spring Boot 3.x**
* **Spring AI** (Integração com a API do Google Gemini via Structured Outputs)
* **Spring WebFlux / WebClient** (Consumo assíncrono da API do TMDB)
* **Global Exception Handling & CORS** (Tratamento centralizado de exceções e regras globais de acesso)

### Frontend
* **Angular 18+** (Componentes Standalone & RxJS)
* **Angular Material** (Ícones, spinners e elementos visuais)
* **SCSS** (Layout responsivo com suporte a Glassmorphism e tema dark com gradiente)

---

## 🛠️ Arquitetura e Fluxo da Aplicação

1. **Entrada do Usuário:** O usuário digita o título ou uma descrição informal de enredo na barra centralizada do frontend.
2. **Processamento da IA (`AiSearchService`):** O backend envia a frase para o **Gemini 3.8-Flash**, que extrai e infere os parâmetros de busca (`query`, `year`, `mediaType`).
3. **Orquestração e Recomendações (`MovieOrchestratorService`):** O sistema realiza a busca primária na API do **TMDB** (`/search/multi`) e enriquece os resultados com obras recomendadas (`/recommendations`).
4. **Mecanismo de Fallback:** Caso a IA esteja indisponível ou ocorra falha na inferência, a busca é redirecionada automaticamente para a pesquisa direta por texto do TMDB.

---

## 📋 Pré-requisitos

* **Java 17** ou superior instalado
* **Node.js** (v18+) e **Angular CLI**
* Chave de API do **Google GenAI / Gemini**
* Chave de API / Bearer Token do **TMDB**

---

## 🔧 Configuração e Execução

### 1. Configurar o Backend (Spring Boot)

No arquivo `src/main/resources/application.yml`:

```yaml
spring:
  ai:
    google:
      genai:
        api-key: SUA_GEMINI_API_KEY

tmdb:
  api:
    token: SEU_TMDB_BEARER_TOKEN
    base-url: https://api.themoviedb.org/3
```

Execute o backend:
```bash
./mvnw spring-boot:run
```
O servidor estará rodando em `http://localhost:8080`.

---

### 2. Configurar o Frontend (Angular)

Navegue até a pasta do projeto Angular, instale as dependências e inicie o servidor de desenvolvimento:

```bash
npm install
ng serve
```

Acesse no navegador: `http://localhost:4200`

---

## 📝 Licença

Este projeto é de uso livre para fins educacionais e de estudo.
package com.devpedrogo.aimoviesearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "AI Movie Searcher API",
		version = "1.0",
		description = "Busca filmes e séries por título ou descrição em linguagem natural."
))
public class AimoviesearcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(AimoviesearcherApplication.class, args);
	}

}

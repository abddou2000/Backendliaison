package com.example.login.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Application API",
        version = "1.0",
        description = "Documentation for the application API",
        contact = @Contact(name = "Support", email = "support@example.com")
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
                .title("Application API")
                .version("1.0")
                .description("Documentation for the application API"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

@Bean
public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("Public")
        .pathsToMatch("/api/login", "/api/hello")
        .build();
}

@Bean
public GroupedOpenApi employeApi() {
    return GroupedOpenApi.builder()
        .group("Employe")
        .pathsToMatch(
            "/api/employes/**",
            "/api/profils-salariaux/**",
            "/api/remunerations/**",
            "/api/conges/**",
            "/api/solde-conges/**",
            "/api/fiches-paie/**"
        )
        .build();
}

@Bean
public GroupedOpenApi administrationApi() {
    return GroupedOpenApi.builder()
        .group("Administration")
        .pathsToMatch(
            "/api/admin/**",
            "/api/configurateurs/**",
            "/api/parametres-systeme/**",
            "/api/sauvegardes/**"
        )
        .build();
}

@Bean
public GroupedOpenApi referentielApi() {
    return GroupedOpenApi.builder()
        .group("Referentiel")
        .pathsToMatch(
            "/api/categories-salariales/**",
            "/api/statuts-salariaux/**",
            "/api/types-contrats/**",
            "/api/unites/**",
            "/api/societes/**",
            "/api/grilles-salariales/**",
            "/api/echelons/**"
        )
        .build();
}

@Bean
public GroupedOpenApi paieApi() {
    return GroupedOpenApi.builder()
        .group("Paie")
        .pathsToMatch(
            "/api/cotisations/**",
            "/api/rubriques-paie/**",
            "/api/primes-indemnites/**",
            "/api/types-prime/**",
            "/api/elements-variables/**",
            "/api/constantes/**"
        )
        .build();
}

@Bean
public GroupedOpenApi documentsApi() {
    return GroupedOpenApi.builder()
        .group("Documents")
        .pathsToMatch(
            "/api/attestations/**",
            "/api/demandes-documents/**",
            "/api/types-attestation/**"
        )
        .build();
}
}
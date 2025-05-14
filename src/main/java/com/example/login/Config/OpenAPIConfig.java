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
            .group("public")
            .pathsToMatch("/api/login", "/api/hello", "/api/cotisations/**", 
                         "/api/statuts-salariaux/**", "/api/categories-salariales/**",
                         "/api/types-contrats/**", "/api/attestations/**", 
                         "/api/unites/**", "/api/societes/**")
            .build(); 
    }
    
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("admin")
            .pathsToMatch("/api/admin/**")
            .build();
    }
}
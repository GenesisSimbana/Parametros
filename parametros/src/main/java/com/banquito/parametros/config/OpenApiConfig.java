package com.banquito.parametros.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Parámetros - Préstamos Automotrices")
                        .description("API para la gestión de parámetros de préstamos automotrices de BanQuito. " +
                                "Incluye gestión de productos de crédito, tasas de interés y documentos requeridos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("BanQuito - Equipo de Desarrollo")
                                .email("desarrollo@banquito.com")
                                .url("https://www.banquito.com"))
                        .license(new License()
                                .name("Licencia Interna BanQuito")
                                .url("https://www.banquito.com/licencia")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api-parametros.banquito.com")
                                .description("Servidor de Producción")
                ));
    }
} 
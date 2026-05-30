package com.skymind.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI skyMindOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SkyMind AI API")
                        .description("""
                                AI-powered flight search and recommendation platform.

                                Features:
                                - Flight Search
                                - AI Recommendation
                                - Flight Ranking
                                - Natural Language Search
                                - Trip Summary Generation
                                - User Travel Preferences
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SkyMind")
                                .email("ujjwalkumarj17@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("SkyMind Documentation"));
    }
}
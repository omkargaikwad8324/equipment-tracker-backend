package com.shivswarajya.equipmenttracker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI equipmentTrackerOpenAPI() {

        final String securitySchemeName = "BearerAuth";

        return new OpenAPI()

                .info(new Info()

                        .title("ShivSwarajya Earthmovers ERP API")

                        .description("Equipment Tracking & Billing Management System")

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Omkar Pawar")
                                .email("admin@earthmovers.com")))

                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))

                .components(new Components()

                        .addSecuritySchemes(
                                securitySchemeName,

                                new SecurityScheme()

                                        .name(securitySchemeName)

                                        .type(SecurityScheme.Type.HTTP)

                                        .scheme("bearer")

                                        .bearerFormat("JWT")));
    }
}
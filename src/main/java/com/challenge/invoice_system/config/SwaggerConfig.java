package com.challenge.invoice_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Invoice System")
                        .version("1.0")
                        .description("API para la gestión de facturas y clientes - Challenge Técnico EXPERTA SEGUROS"));
    }
}
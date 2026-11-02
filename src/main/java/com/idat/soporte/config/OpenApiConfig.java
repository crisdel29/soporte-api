package com.idat.soporte.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("API de solicitudes de soporte técnico")
                .version("1.0.0")
                .description("API RESTful para registrar, consultar, actualizar y eliminar "
                        + "solicitudes de soporte técnico. Los datos se almacenan en memoria.")
                .contact(new Contact().name("Equipo de desarrollo backed - IDAT")));
    }
}

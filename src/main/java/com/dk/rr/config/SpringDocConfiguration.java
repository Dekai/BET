package com.dk.rr.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Project API")
                        .description("Dekai demo project")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://zhangdekai.com/cv.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Wiki Documentation")
                        .url("https://xxx.com/docs"));
    }
}

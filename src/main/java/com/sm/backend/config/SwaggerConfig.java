package com.sm.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stadium Manager API")
                        .description("Stadium backend REST API documentation")
                        .version("v1")
                        .contact(new Contact()
                                .name("Stadium Backend Team"))
                        .license(new License()
                                .name("Apache 2.0")));
    }
}

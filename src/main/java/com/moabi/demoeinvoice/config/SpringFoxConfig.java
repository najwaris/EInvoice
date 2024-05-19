package com.moabi.demoeinvoice.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringFoxConfig {
    private static final String securitySchemeName = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setName("Test Name");
        contact.setEmail("Test Email");
        contact.setUrl("Test Url");

        Info info = new Info()
                .title("Demo e-Invoice")
                .version("0.0.1")
                .contact(contact)
                .description("Test description");

        Components components = new Components().addSecuritySchemes("JWT",
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .name(securitySchemeName));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .info(info)
                .components(components);
    }
}

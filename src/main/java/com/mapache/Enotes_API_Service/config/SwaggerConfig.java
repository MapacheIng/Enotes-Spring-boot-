package com.mapache.Enotes_API_Service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openApi = new OpenAPI();
        Info info = new Info();
        info.setTitle("Enotes API");
        info.setDescription("Enotes-API Documentation");
        info.setVersion("1.0.0");
        info.setTermsOfService("https://example.com/terms");
        info.setContact(new Contact().email("brandox1002@gmail.com")
                .name("MapacheIng")
                .url("https://github.com/MapacheIng"));
        info.setLicense(new License().name("Enotes 1.0").url("https://github.com/MapacheIng"));

        List<Server> serverList = List.of(new Server().description("Dev").url("http://localhost:8081"),
                new Server().description("Test").url("http://localhost:8082"),
                new Server().description("Prod").url("http://localhost:8083"));


        SecurityScheme securityScheme = new SecurityScheme().name("Authorization")
                        .scheme("bearer").type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT").in(SecurityScheme.In.HEADER);

        Components components = new Components().addSecuritySchemes("Token", securityScheme);

        openApi.setInfo(info);
        openApi.setServers(serverList);
        openApi.setComponents(components);
        openApi.setSecurity(List.of(new SecurityRequirement().addList("Token")));

        return openApi;
    }

}

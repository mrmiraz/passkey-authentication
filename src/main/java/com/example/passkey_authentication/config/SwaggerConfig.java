package com.example.passkey_authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class SwaggerConfig {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Mahmudur Rahman Miraz");
        myContact.setEmail("miraz.hossain@seu.edu.bd");

        Info information = new Info()
                .title("Passkey API Documentation")
                .version("1.0")
                .description("This API exposes endpoints to manage passkey.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}

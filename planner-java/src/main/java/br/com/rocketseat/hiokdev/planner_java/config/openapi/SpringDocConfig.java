package br.com.rocketseat.hiokdev.planner_java.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Planner API", description = "Planner API - Plan your trips", version = "1.0.0",
                contact = @Contact(name = "Planner Support", email = "fake-support@planner.com.br"),
                license = @License(name = "Licence: Contact us")
        ),
        servers = {@Server(url = "http://localhost:8080", description = "local")},
        tags = {
                @Tag(name = "Trip", description = "Trip endpoints"),
                @Tag(name = "Participant", description = "Participant endpoints")
        }
)
public class SpringDocConfig {
}

package br.com.rocketseat.hiokdev.planner_java.api.link.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.link.Link;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

public record LinkRequestPayload(
        @Schema(example = "O que fazer: Florianópolis") @NotBlank @Size(min = 4, max = 255) String title,
        @Schema(example = "https://www.tripadvisor.com.br/Attractions-g303576-Activities-Florianopolis_State_of_Santa_Catarina.html") @NotBlank @URL String url
) {

    @Builder
    public LinkRequestPayload {
    }

    public static Link toDomain(LinkRequestPayload payload) {
        return Link.builder()
                .title(payload.title())
                .url(payload.url())
                .build();
    }

}

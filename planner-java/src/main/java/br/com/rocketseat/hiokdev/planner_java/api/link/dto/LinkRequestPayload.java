package br.com.rocketseat.hiokdev.planner_java.api.link.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.link.Link;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record LinkRequestPayload(
        @NotBlank @Size(min = 4, max = 255) String title,
        @NotBlank @URL String url
) {

    public static Link toDomain(LinkRequestPayload payload) {
        return Link.builder()
                .title(payload.title())
                .url(payload.url())
                .build();
    }

}

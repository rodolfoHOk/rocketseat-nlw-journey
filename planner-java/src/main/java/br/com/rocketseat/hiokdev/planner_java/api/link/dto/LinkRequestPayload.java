package br.com.rocketseat.hiokdev.planner_java.api.link.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.link.Link;

public record LinkRequestPayload(
        String title,
        String url
) {

    public static Link toDomain(LinkRequestPayload payload) {
        return Link.builder()
                .title(payload.title())
                .url(payload.url())
                .build();
    }

}

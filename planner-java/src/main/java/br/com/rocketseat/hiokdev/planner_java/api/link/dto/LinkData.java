package br.com.rocketseat.hiokdev.planner_java.api.link.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.link.Link;
import lombok.Builder;

import java.util.UUID;

public record LinkData(
        UUID id,
        String title,
        String url
) {

    @Builder
    public LinkData {
    }

    public static LinkData toResponse(Link link) {
        return LinkData.builder()
                .id(link.getId())
                .title(link.getTitle())
                .url(link.getUrl())
                .build();
    }

}

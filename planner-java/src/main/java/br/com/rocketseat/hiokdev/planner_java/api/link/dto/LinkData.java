package br.com.rocketseat.hiokdev.planner_java.api.link.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.link.Link;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

public record LinkData(
        @Schema(example = "fb478a6f-a1f7-4553-b466-26b299961181") UUID id,
        @Schema(example = "O que fazer: Florianópolis") String title,
        @Schema(example = "https://www.tripadvisor.com.br/Attractions-g303576-Activities-Florianopolis_State_of_Santa_Catarina.html") String url
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

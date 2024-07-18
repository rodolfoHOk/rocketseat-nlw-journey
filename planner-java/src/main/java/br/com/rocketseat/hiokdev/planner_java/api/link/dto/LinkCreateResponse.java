package br.com.rocketseat.hiokdev.planner_java.api.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record LinkCreateResponse(
        @Schema(example = "fb478a6f-a1f7-4553-b466-26b299961181") UUID linkId
) {
}

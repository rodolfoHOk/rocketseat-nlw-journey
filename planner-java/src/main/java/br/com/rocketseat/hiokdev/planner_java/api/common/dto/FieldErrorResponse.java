package br.com.rocketseat.hiokdev.planner_java.api.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record FieldErrorResponse(
        @Schema(example = "starts_at") String name,
        @Schema(example = "data informada já passou") String message
) {

    @Builder
    public FieldErrorResponse {
    }

}

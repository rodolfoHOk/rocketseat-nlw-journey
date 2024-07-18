package br.com.rocketseat.hiokdev.planner_java.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record ProblemResponse(
        @Schema(example = "400") Integer status,
        @Schema(example = "Invalid request content") String description,
        @Schema(example = "/trips") String path,
        @Schema(example = "2024-07-18T14:33:48.664281108") LocalDateTime timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<FieldErrorResponse> fields
) {

    @Builder
    public ProblemResponse {
    }

}

package br.com.rocketseat.hiokdev.planner_java.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record ProblemResponse(
        Integer status,
        String description,
        LocalDateTime timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<FieldErrorResponse> fields
) {

    @Builder
    public ProblemResponse {
    }

}

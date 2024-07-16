package br.com.rocketseat.hiokdev.planner_java.api.common.dto;

import lombok.Builder;

public record FieldErrorResponse(
        String name,
        String message
) {

    @Builder
    public FieldErrorResponse {
    }

}

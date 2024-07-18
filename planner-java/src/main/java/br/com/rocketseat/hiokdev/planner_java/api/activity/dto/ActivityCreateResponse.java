package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ActivityCreateResponse(
        @Schema(example = "343dc7ad-a4c3-473f-995f-086e0692b59b") UUID activityId
) {
}

package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record DayActivities(
        @Schema(example = "2024-07-24T12:00:00") LocalDateTime date,
        List<ActivityData> activities
) {
}

package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(
        UUID id,
        String title,
        LocalDateTime occursAt
) {
}

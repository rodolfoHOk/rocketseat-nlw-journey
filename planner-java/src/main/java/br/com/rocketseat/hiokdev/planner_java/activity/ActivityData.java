package br.com.rocketseat.hiokdev.planner_java.activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(
        UUID id,
        String title,
        LocalDateTime occursAt
) {
}

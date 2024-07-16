package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.Activity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ActivityRequestPayload(
        String title,
        String occurs_at
) {

    @Builder
    public ActivityRequestPayload {
    }

    public static Activity toDomain(ActivityRequestPayload payload) {
        return Activity.builder()
                .title(payload.title())
                .occursAt(LocalDateTime.parse(payload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

}

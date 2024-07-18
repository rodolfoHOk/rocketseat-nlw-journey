package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import br.com.rocketseat.hiokdev.planner_java.config.validation.ValidDateTime;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ActivityRequestPayload(
        @Schema(example = "Check-in no hotel") @NotBlank @Size(min = 4, max = 255) String title,
        @Schema(example = "2024-07-24T12:00:00Z") @NotBlank @ValidDateTime String occurs_at
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

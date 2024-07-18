package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.Activity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(
        @Schema(example = "343dc7ad-a4c3-473f-995f-086e0692b59b") UUID id,
        @Schema(example = "Check-in no hotel") String title,
        @Schema(example = "2024-07-24T12:00:00") @JsonProperty("occurs_at") LocalDateTime occursAt
) {

    @Builder
    public ActivityData {
    }

    public static ActivityData toResponse(Activity activity) {
        return ActivityData.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .occursAt(activity.getOccursAt())
                .build();
    }

}

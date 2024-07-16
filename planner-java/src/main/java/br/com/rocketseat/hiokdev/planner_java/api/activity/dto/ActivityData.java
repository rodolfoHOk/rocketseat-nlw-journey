package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.Activity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(
        UUID id,
        String title,
        LocalDateTime occursAt
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

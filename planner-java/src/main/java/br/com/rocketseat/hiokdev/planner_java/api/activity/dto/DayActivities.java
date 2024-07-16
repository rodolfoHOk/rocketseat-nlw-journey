package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import java.time.LocalDateTime;
import java.util.List;

public record DayActivities(
        LocalDateTime date,
        List<ActivityData> activities
) {
}

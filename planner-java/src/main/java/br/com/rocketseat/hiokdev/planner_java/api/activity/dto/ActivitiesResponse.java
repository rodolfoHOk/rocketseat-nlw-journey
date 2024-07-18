package br.com.rocketseat.hiokdev.planner_java.api.activity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ActivitiesResponse {

    private List<DayActivities> activities;

    public ActivitiesResponse(List<ActivityData> activityDataList) {
        List<DayActivities> toResponse = new ArrayList<>();
        LocalDateTime currentDay = activityDataList.getFirst().occursAt();
        List<ActivityData> dayActivities = new ArrayList<>();
        for (ActivityData activityData : activityDataList) {
            if (activityData.occursAt().toLocalDate().isEqual(currentDay.toLocalDate())) {
                dayActivities.add(activityData);
            } else {
                toResponse.add(new DayActivities(currentDay, dayActivities));
                dayActivities = new ArrayList<>();
                currentDay = activityData.occursAt();
                dayActivities.add(activityData);
            }
        }
        toResponse.add(new DayActivities(currentDay, dayActivities));
        this.activities = toResponse;
    }

    public static ActivitiesResponse empty() {
        var response = new ActivitiesResponse();
        response.setActivities(List.of());
        return response;
    }

}

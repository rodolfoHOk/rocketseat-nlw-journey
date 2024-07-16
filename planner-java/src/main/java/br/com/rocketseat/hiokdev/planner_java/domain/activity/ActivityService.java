package br.com.rocketseat.hiokdev.planner_java.domain.activity;

import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityData;
import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityResponse;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);
        this.activityRepository.save(newActivity);
        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesByTripId(UUID tripId) {
        return this.activityRepository.findByTripId(tripId).stream()
                .map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt()))
                .toList();
    }

}

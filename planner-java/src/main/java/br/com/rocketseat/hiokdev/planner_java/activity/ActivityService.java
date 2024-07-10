package br.com.rocketseat.hiokdev.planner_java.activity;

import br.com.rocketseat.hiokdev.planner_java.trip.Trip;
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

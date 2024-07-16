package br.com.rocketseat.hiokdev.planner_java.domain.activity;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final TripQueryService tripQueryService;

    public Activity registerActivity(Activity activity, UUID tripId) {
        var trip = this.tripQueryService.getById(tripId);
        activity.setTrip(trip);
        return this.activityRepository.save(activity);
    }

    public List<Activity> getAllActivitiesByTripId(UUID tripId) {
        return this.activityRepository.findByTripId(tripId);
    }

}

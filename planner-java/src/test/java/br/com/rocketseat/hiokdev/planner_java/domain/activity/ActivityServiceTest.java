package br.com.rocketseat.hiokdev.planner_java.domain.activity;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.ActivityFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private TripQueryService tripQueryService;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void shouldReturnActivityWhenRegisterActivity() {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripQueryService.getById(tripId)).thenReturn(trip);
        when(activityRepository.save(any())).thenAnswer(invocationOnMock -> {
            var activity = invocationOnMock.getArgument(0, Activity.class);
            activity.setId(UUID.randomUUID());
            return activity;
        });

        var activity = ActivityFactory.getActivityWithoutId();
        var sutActivity = activityService.registerActivity(activity, tripId);

        assertThat(sutActivity.getId()).isNotNull();
        assertThat(sutActivity.getTitle()).isEqualTo(activity.getTitle());
        assertThat(sutActivity.getOccursAt()).isEqualTo(activity.getOccursAt());
        assertThat(sutActivity.getTrip()).isEqualTo(trip);
        verify(tripQueryService).getById(any());
        verify(activityRepository).save(any());
    }

    @Test
    void shouldThrowValidationExceptionActivityWhenRegisterActivityWithBeforeTripDate() {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripQueryService.getById(tripId)).thenReturn(trip);

        var activity = ActivityFactory.getActivityWithBeforeTripDate();
        assertThatThrownBy(() -> activityService.registerActivity(activity, tripId)).isInstanceOf(ValidationException.class);

        verify(tripQueryService).getById(any());
        verify(activityRepository, times(0)).save(any());
    }

    @Test
    void shouldThrowValidationExceptionActivityWhenRegisterActivityWithAfterTripDate() {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripQueryService.getById(tripId)).thenReturn(trip);

        var activity = ActivityFactory.getActivityWithAfterTripDate();
        assertThatThrownBy(() -> activityService.registerActivity(activity, tripId)).isInstanceOf(ValidationException.class);

        verify(tripQueryService).getById(any());
        verify(activityRepository, times(0)).save(any());
    }

    @Test
    void shouldReturnActivitiesListWhenGetAllActivitiesByTripId() {
        var tripId = UUID.randomUUID();
        var activities = ActivityFactory.getActivityList(tripId);
        when(activityRepository.findByTripIdOrderByOccursAtAsc(tripId)).thenReturn(activities);

        var sutActivities = activityService.getAllActivitiesByTripId(tripId);

        assertThat(sutActivities).usingRecursiveComparison().isEqualTo(activities);
        verify(activityRepository).findByTripIdOrderByOccursAtAsc(any());
    }

}

package br.com.rocketseat.hiokdev.planner_java.factory.domain;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.Activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ActivityFactory {

    public static Activity getActivityWithId(UUID id) {
        return getActivityWithoutId().toBuilder().id(id).build();
    }

    public static Activity getActivityWithBeforeTripDate() {
        return getActivityWithoutId().toBuilder().occursAt(LocalDateTime.now().plusHours(23).plusMinutes(59)).build();
    }

    public static Activity getActivityWithAfterTripDate() {
        return getActivityWithoutId().toBuilder().occursAt(LocalDateTime.now().plusDays(6).plusMinutes(1)).build();
    }

    public static Activity getActivityWithoutId() {
        return Activity.builder()
                .title("Check-in in Hotel")
                .occursAt(LocalDateTime.now().plusDays(1).plusHours(4))
                .build();
    }

    public static List<Activity> getActivityList(UUID tripId) {
        var trip = TripFactory.getTripWithId(tripId);
        return List.of(
                Activity.builder()
                        .id(UUID.randomUUID())
                        .title("Check-in Hotel")
                        .occursAt(LocalDateTime.now().plusDays(1).plusHours(4))
                        .trip(trip)
                        .build(),
                Activity.builder()
                        .id(UUID.randomUUID())
                        .title("Conference day 1")
                        .occursAt(LocalDateTime.now().plusDays(1).plusHours(8))
                        .trip(trip)
                        .build(),
                Activity.builder()
                        .id(UUID.randomUUID())
                        .title("Conference day 2")
                        .occursAt(LocalDateTime.now().plusDays(2).plusHours(8))
                        .trip(trip)
                        .build()
        );
    }

}

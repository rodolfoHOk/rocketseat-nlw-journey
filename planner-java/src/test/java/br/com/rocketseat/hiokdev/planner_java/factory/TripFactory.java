package br.com.rocketseat.hiokdev.planner_java.factory;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;

import java.time.LocalDateTime;
import java.util.UUID;

public class TripFactory {

    public static Trip getTripWithId(UUID id) {
        return getTripWithoutId().toBuilder()
                .id(id)
                .build();
    }

    public static Trip getTripWithInvalidStartsAt() {
        return getTripWithoutId().toBuilder()
                .startsAt(LocalDateTime.of(2023, 12, 5, 10, 30))
                .build();
    }

    public static Trip getTripWithInvalidEndsAt() {
        return getTripWithoutId().toBuilder()
                .endsAt(LocalDateTime.of(2023, 12, 10, 10, 30))
                .build();
    }

    public static Trip getTripWithEndsAtBeforeStartsAt() {
        return getTripWithoutId().toBuilder()
                .endsAt(LocalDateTime.of(2024, 12, 5, 10, 0))
                .build();
    }

    public static Trip getTripToUpdate() {
        return Trip.builder()
                .destination("Guarujá, SP")
                .startsAt(LocalDateTime.of(2024, 12, 4, 10, 30))
                .endsAt(LocalDateTime.of(2024, 12, 9, 11, 0))
                .build();
    }

    public static Trip getTripWithoutId() {
        return Trip.builder()
                .destination("São Paulo, SP")
                .startsAt(LocalDateTime.of(2024, 12, 5, 10, 30))
                .endsAt(LocalDateTime.of(2024, 12, 10, 11, 0))
                .isConfirmed(false)
                .ownerName("João Batista")
                .ownerEmail("batista@email.com")
                .build();
    }

}

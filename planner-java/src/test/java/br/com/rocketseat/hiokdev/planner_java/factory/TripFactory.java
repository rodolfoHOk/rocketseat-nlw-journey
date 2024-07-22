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
                .startsAt(LocalDateTime.now().minusMinutes(1))
                .build();
    }

    public static Trip getTripWithInvalidEndsAt() {
        return getTripWithoutId().toBuilder()
                .endsAt(LocalDateTime.now().minusMinutes(1))
                .build();
    }

    public static Trip getTripWithEndsAtBeforeStartsAt() {
        return getTripWithoutId().toBuilder()
                .endsAt(LocalDateTime.now().plusHours(23).minusMinutes(59))
                .build();
    }

    public static Trip getTripToUpdate() {
        return Trip.builder()
                .destination("Guarujá, SP")
                .startsAt(LocalDateTime.now().plusDays(2))
                .endsAt(LocalDateTime.now().plusDays(7))
                .build();
    }

    public static Trip getTripWithoutId() {
        return Trip.builder()
                .destination("São Paulo, SP")
                .startsAt(LocalDateTime.now().plusDays(1))
                .endsAt(LocalDateTime.now().plusDays(6))
                .isConfirmed(false)
                .ownerName("João Batista")
                .ownerEmail("batista@email.com")
                .build();
    }

}

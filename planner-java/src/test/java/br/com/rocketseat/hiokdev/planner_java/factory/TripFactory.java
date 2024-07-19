package br.com.rocketseat.hiokdev.planner_java.factory;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;

import java.time.LocalDateTime;
import java.util.UUID;

public class TripFactory {

    public static Trip getTripWithId(UUID id) {
        return Trip.builder()
                .id(id)
                .destination("São Paulo, SP")
                .startsAt(LocalDateTime.of(2024, 12, 5, 10, 30))
                .endsAt(LocalDateTime.of(2024, 12, 10, 11, 0))
                .isConfirmed(false)
                .ownerName("João Batista")
                .ownerEmail("joaobatista@email.com")
                .build();
    }

}

package br.com.rocketseat.hiokdev.planner_java.api.trip.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TripUpdateRequestPayload(
        String destination,
        String starts_at,
        String ends_at
) {

    public static Trip toDomain(TripUpdateRequestPayload payload) {
        return Trip.builder()
                .destination(payload.destination)
                .startsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME))
                .endsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

}

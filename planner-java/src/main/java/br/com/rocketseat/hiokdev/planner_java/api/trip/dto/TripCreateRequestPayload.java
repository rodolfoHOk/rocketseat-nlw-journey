package br.com.rocketseat.hiokdev.planner_java.api.trip.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record TripCreateRequestPayload(
        String destination,
        String starts_at,
        String ends_at,
        String owner_email,
        String owner_name,
        List<String> emails_to_invite
) {

    public static Trip toDomain(TripCreateRequestPayload payload) {
        return Trip.builder()
                .destination(payload.destination)
                .startsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME))
                .endsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME))
                .isConfirmed(false)
                .ownerName(payload.owner_name())
                .ownerEmail(payload.owner_email())
                .build();
    }

}

package br.com.rocketseat.hiokdev.planner_java.api.trip.dto;

import br.com.rocketseat.hiokdev.planner_java.config.validation.ValidDateTime;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record TripCreateRequestPayload(
        @NotBlank @Size(min = 4, max = 255) String destination,
        @NotBlank @ValidDateTime String starts_at,
        @NotBlank @ValidDateTime String ends_at,
        @NotBlank @Email String owner_email,
        @NotBlank String owner_name,
        @Size(min = 1) List<@Email String> emails_to_invite
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

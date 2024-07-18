package br.com.rocketseat.hiokdev.planner_java.api.trip.dto;

import br.com.rocketseat.hiokdev.planner_java.config.validation.ValidDateTime;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record TripCreateRequestPayload(
        @Schema(example = "Florianópolis, SC") @NotBlank @Size(min = 4, max = 255) String destination,
        @Schema(example = "2024-07-24T10:30:00Z") @NotBlank @ValidDateTime String starts_at,
        @Schema(example = "2024-07-29T12:00:00Z") @NotBlank @ValidDateTime String ends_at,
        @Schema(example = "fernanda.kipper@email.com") @NotBlank @Email String owner_email,
        @Schema(example = "Fernanda Kipper") @NotBlank String owner_name,
        @Schema(example = "[\"mayk.brito@email.com\", \"rodrigo.goncalves@email.com\"]")
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

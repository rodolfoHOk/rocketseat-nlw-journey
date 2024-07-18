package br.com.rocketseat.hiokdev.planner_java.api.trip.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record TripData(
        @Schema(example = "bfdc243f-6a1f-4e33-b9f1-5f134a4316a5") UUID id,
        @Schema(example = "Florianópolis, SC") String destination,
        @Schema(example = "2024-07-24T10:30:00") @JsonProperty("starts_at") LocalDateTime startsAt,
        @Schema(example = "2024-07-29T12:00:00") @JsonProperty("ends_at") LocalDateTime endsAt,
        @Schema(example = "false") @JsonProperty("is_confirmed") Boolean isConfirmed
) {

    @Builder
    public TripData {
    }

    public static TripData toResponse(Trip trip) {
        return TripData.builder()
                .id(trip.getId())
                .destination(trip.getDestination())
                .startsAt(trip.getStartsAt())
                .endsAt(trip.getEndsAt())
                .isConfirmed(trip.getIsConfirmed())
                .build();
    }

}

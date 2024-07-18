package br.com.rocketseat.hiokdev.planner_java.api.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record TripCreateResponse(
        @Schema(example = "bfdc243f-6a1f-4e33-b9f1-5f134a4316a5") UUID tripId
) {
}

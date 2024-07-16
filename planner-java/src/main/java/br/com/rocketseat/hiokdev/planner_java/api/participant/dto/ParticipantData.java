package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import java.util.UUID;

public record ParticipantData(
        UUID id,
        String name,
        String email,
        Boolean isConfirmed
) {
}

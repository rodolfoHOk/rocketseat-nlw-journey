package br.com.rocketseat.hiokdev.planner_java.participant;

import java.util.UUID;

public record ParticipantData(
        UUID id,
        String name,
        String email,
        Boolean isConfirmed
) {
}

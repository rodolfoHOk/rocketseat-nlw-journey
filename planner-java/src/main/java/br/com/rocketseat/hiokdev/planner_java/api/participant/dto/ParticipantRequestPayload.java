package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import lombok.Builder;

public record ParticipantRequestPayload(
        String name,
        String email
) {

    @Builder
    public ParticipantRequestPayload {
    }

    public static Participant toDomain(ParticipantRequestPayload payload) {
        return Participant.builder()
                .name(payload.name())
                .email(payload.email())
                .build();
    }

}

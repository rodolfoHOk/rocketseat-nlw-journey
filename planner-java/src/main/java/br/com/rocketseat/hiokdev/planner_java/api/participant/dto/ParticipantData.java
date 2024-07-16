package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

public record ParticipantData(
        UUID id,
        String name,
        String email,
        @JsonProperty("is_confirmed") Boolean isConfirmed
) {

    @Builder
    public ParticipantData {
    }

    public static ParticipantData toResponse(Participant participant) {
        return ParticipantData.builder()
                .id(participant.getId())
                .name(participant.getName())
                .email(participant.getEmail())
                .isConfirmed(participant.getIsConfirmed())
                .build();
    }

}

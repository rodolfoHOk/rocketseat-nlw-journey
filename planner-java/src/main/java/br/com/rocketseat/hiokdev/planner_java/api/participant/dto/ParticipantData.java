package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

public record ParticipantData(
        @Schema(example = "145ba395-04db-42eb-95c0-e7a7da089997") UUID id,
        @Schema(example = "Mayk Brito") String name,
        @Schema(example = "mayk.brito@mail.com") String email,
        @Schema(example = "true") @JsonProperty("is_confirmed") Boolean isConfirmed
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

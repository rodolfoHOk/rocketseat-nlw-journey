package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ParticipantCreateResponse(
        @Schema(example = "36d64cd6-58be-40ce-90aa-afca2f22918a") UUID participantId
) {
}

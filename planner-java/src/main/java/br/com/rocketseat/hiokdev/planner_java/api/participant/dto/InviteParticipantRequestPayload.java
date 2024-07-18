package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteParticipantRequestPayload(
        @Schema(example = "diego.fernandes@email.com") @NotBlank @Email String email
) {
}

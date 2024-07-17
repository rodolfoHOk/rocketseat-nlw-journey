package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InviteParticipantRequestPayload(
        @NotBlank @Email String email
) {
}

package br.com.rocketseat.hiokdev.planner_java.api.participant.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record ParticipantRequestPayload(
        @Schema(example = "Mayk Brito") @NotBlank @Size(min=3, max = 255) String name,
        @Schema(example = "mayk.brito@mail.com") @Email String email
) {

    @Builder(toBuilder = true)
    public ParticipantRequestPayload {
    }

    public static Participant toDomain(ParticipantRequestPayload payload) {
        return Participant.builder()
                .name(payload.name())
                .email(payload.email())
                .build();
    }

}

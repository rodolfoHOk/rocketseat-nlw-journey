package br.com.rocketseat.hiokdev.planner_java.factory.dto;

import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.InviteParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.EmailFactory;

public class InviteParticipantRequestPayloadFactory {

    public static InviteParticipantRequestPayload getPayload() {
        return new InviteParticipantRequestPayload(EmailFactory.getEmail());
    }

    public static InviteParticipantRequestPayload getPayloadWithBlankEmail() {
        return new InviteParticipantRequestPayload("");
    }

    public static InviteParticipantRequestPayload getPayloadWithInvalidEmail() {
        return new InviteParticipantRequestPayload("invalid.email.com");
    }

}

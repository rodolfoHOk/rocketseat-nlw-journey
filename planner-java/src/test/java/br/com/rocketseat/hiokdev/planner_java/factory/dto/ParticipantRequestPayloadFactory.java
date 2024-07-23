package br.com.rocketseat.hiokdev.planner_java.factory.dto;

import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantRequestPayload;

public class ParticipantRequestPayloadFactory {

    public static ParticipantRequestPayload getPayload() {
        return ParticipantRequestPayload.builder()
                .name("Maria Santos")
                .email("maria@email.com")
                .build();
    }

    public static ParticipantRequestPayload getPayloadWithOnlyName() {
        return ParticipantRequestPayload.builder()
                .name("Maria Santos")
                .build();
    }

    public static ParticipantRequestPayload getPayloadWithBlankName() {
        return ParticipantRequestPayload.builder()
                .name("")
                .email("maria@email.com")
                .build();
    }

    public static ParticipantRequestPayload getPayloadWithShortName() {
        return ParticipantRequestPayload.builder()
                .name("Ma")
                .email("maria@email.com")
                .build();
    }

    public static ParticipantRequestPayload getPayloadWithLongName() {
        return ParticipantRequestPayload.builder()
                .name("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce mattis mauris vel faucibus finibus. Aliquam nisl mi, feugiat quis lorem id, cursus gravida risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus imperdiet urna nec ullamcorper aliquet.")
                .email("maria@email.com")
                .build();
    }

    public static ParticipantRequestPayload getPayloadWithInvalidEmail() {
        return ParticipantRequestPayload.builder()
                .name("Maria Santos")
                .email("invalid.email.com")
                .build();
    }

}

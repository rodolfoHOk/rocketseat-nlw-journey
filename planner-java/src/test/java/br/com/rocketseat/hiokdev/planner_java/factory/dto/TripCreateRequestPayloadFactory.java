package br.com.rocketseat.hiokdev.planner_java.factory.dto;

import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripCreateRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.EmailFactory;

import java.time.LocalDateTime;
import java.util.List;

public class TripCreateRequestPayloadFactory {

    public static TripCreateRequestPayload getPayload() {
        return TripCreateRequestPayload.builder()
                .destination("São Paulo, SP")
                .starts_at(LocalDateTime.now().plusDays(1).toString())
                .ends_at(LocalDateTime.now().plusDays(6).toString())
                .emails_to_invite(EmailFactory.getEmailList())
                .owner_name("João Batista")
                .owner_email("batista@email.com")
                .build();
    }

    public static TripCreateRequestPayload getPayloadWithBlankDestination() {
        return getPayload().toBuilder().destination("").build();
    }

    public static TripCreateRequestPayload getPayloadWithBlankStartsAt() {
        return getPayload().toBuilder().starts_at("").build();
    }

    public static TripCreateRequestPayload getPayloadWithBlankEndsAt() {
        return getPayload().toBuilder().ends_at("").build();
    }

    public static TripCreateRequestPayload getPayloadWithBlankOwnerName() {
        return getPayload().toBuilder().owner_name("").build();
    }

    public static TripCreateRequestPayload getPayloadWithBlankOwnerEmail() {
        return getPayload().toBuilder().owner_email("").build();
    }

    public static TripCreateRequestPayload getPayloadWithEmptyEmailsToInvite() {
        return getPayload().toBuilder().emails_to_invite(List.of()).build();
    }

    public static TripCreateRequestPayload getPayloadWithShortDestination() {
        return getPayload().toBuilder().destination("Abc").build();
    }

    public static TripCreateRequestPayload getPayloadWithLongDestination() {
        return getPayload().toBuilder()
                .destination("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce mattis mauris vel faucibus finibus. Aliquam nisl mi, feugiat quis lorem id, cursus gravida risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus imperdiet urna nec ullamcorper aliquet.")
                .build();
    }

    public static TripCreateRequestPayload getPayloadWithInvalidStartsAt() {
        return getPayload().toBuilder().starts_at("InvalidStartsAt").build();
    }

    public static TripCreateRequestPayload getPayloadWithInvalidEndsAt() {
        return getPayload().toBuilder().ends_at("InvalidEndsAt").build();
    }

    public static TripCreateRequestPayload getPayloadWithInvalidOwnerEmail() {
        return getPayload().toBuilder().owner_email("InvalidOwnerEmail.com").build();
    }

    public static TripCreateRequestPayload getPayloadWithInvalidEmailsToInvite() {
        return getPayload().toBuilder().emails_to_invite(List.of("valid@email.com", "valid@email.com", "invalid.email.com")).build();
    }

}

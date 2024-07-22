package br.com.rocketseat.hiokdev.planner_java.factory.dto;

import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripUpdateRequestPayload;

import java.time.LocalDateTime;

public class TripUpdateRequestPayloadFactory {

    public static TripUpdateRequestPayload getPayload() {
        return TripUpdateRequestPayload.builder()
                .destination("Guarujá, SP")
                .starts_at(LocalDateTime.now().plusDays(2).toString())
                .ends_at(LocalDateTime.now().plusDays(7).toString())
                .build();
    }

    public static TripUpdateRequestPayload getPayloadWithBlankDestination() {
        return getPayload().toBuilder().destination("").build();
    }

    public static TripUpdateRequestPayload getPayloadWithBlankStartsAt() {
        return getPayload().toBuilder().starts_at("").build();
    }

    public static TripUpdateRequestPayload getPayloadWithBlankEndsAt() {
        return getPayload().toBuilder().ends_at("").build();
    }

    public static TripUpdateRequestPayload getPayloadWithShortDestination() {
        return getPayload().toBuilder().destination("Abc").build();
    }

    public static TripUpdateRequestPayload getPayloadWithLongDestination() {
        return getPayload().toBuilder()
                .destination("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce mattis mauris vel faucibus finibus. Aliquam nisl mi, feugiat quis lorem id, cursus gravida risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus imperdiet urna nec ullamcorper aliquet.")
                .build();
    }

    public static TripUpdateRequestPayload getPayloadWithInvalidStartsAt() {
        return getPayload().toBuilder().starts_at("InvalidStartsAt").build();
    }

    public static TripUpdateRequestPayload getPayloadWithInvalidEndsAt() {
        return getPayload().toBuilder().ends_at("InvalidEndsAt").build();
    }

}

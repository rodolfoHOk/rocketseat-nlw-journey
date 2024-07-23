package br.com.rocketseat.hiokdev.planner_java.factory.dto;

import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityRequestPayload;

import java.time.LocalDateTime;

public class ActivityRequestPayloadFactory {

    public static ActivityRequestPayload getPayload() {
        return ActivityRequestPayload.builder()
                .title("Check-in in Hotel")
                .occurs_at(LocalDateTime.now().plusDays(1).plusHours(4).toString())
                .build();
    }

    public static ActivityRequestPayload getPayloadWithBlankTitle() {
        return ActivityRequestPayload.builder()
                .title("")
                .occurs_at(LocalDateTime.now().plusDays(1).plusHours(4).toString())
                .build();
    }

    public static ActivityRequestPayload getPayloadWithBlankOccursAt() {
        return ActivityRequestPayload.builder()
                .title("Check-in in Hotel")
                .occurs_at("")
                .build();
    }

    public static ActivityRequestPayload getPayloadWithShortTitle() {
        return ActivityRequestPayload.builder()
                .title("Abc")
                .occurs_at(LocalDateTime.now().plusDays(1).plusHours(4).toString())
                .build();
    }

    public static ActivityRequestPayload getPayloadWithLongTitle() {
        return ActivityRequestPayload.builder()
                .title("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce mattis mauris vel faucibus finibus. Aliquam nisl mi, feugiat quis lorem id, cursus gravida risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus imperdiet urna nec ullamcorper aliquet.")
                .occurs_at(LocalDateTime.now().plusDays(1).plusHours(4).toString())
                .build();
    }

    public static ActivityRequestPayload getPayloadWithLInvalidaOccursAt() {
        return ActivityRequestPayload.builder()
                .title("Check-in in Hotel")
                .occurs_at("InvalidOccursAt")
                .build();
    }

}

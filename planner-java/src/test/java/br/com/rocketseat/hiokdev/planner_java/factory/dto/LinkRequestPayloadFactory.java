package br.com.rocketseat.hiokdev.planner_java.factory.dto;

import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkRequestPayload;

public class LinkRequestPayloadFactory {

    public static LinkRequestPayload getPayload() {
        return LinkRequestPayload.builder()
                .title("Hospedagem booking")
                .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                .build();
    }

    public static LinkRequestPayload getPayloadWithBlankTitle() {
        return LinkRequestPayload.builder()
                .title("")
                .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                .build();
    }

    public static LinkRequestPayload getPayloadWithBlankUrl() {
        return LinkRequestPayload.builder()
                .title("Hospedagem booking")
                .url("")
                .build();
    }

    public static LinkRequestPayload getPayloadWithShortTitle() {
        return LinkRequestPayload.builder()
                .title("Hos")
                .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                .build();
    }

    public static LinkRequestPayload getPayloadWithLongTitle() {
        return LinkRequestPayload.builder()
                .title("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce mattis mauris vel faucibus finibus. Aliquam nisl mi, feugiat quis lorem id, cursus gravida risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus imperdiet urna nec ullamcorper aliquet.")
                .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                .build();
    }

    public static LinkRequestPayload getPayloadWithInvalidUrl() {
        return LinkRequestPayload.builder()
                .title("Hospedagem booking")
                .url("invalidurl")
                .build();
    }

}

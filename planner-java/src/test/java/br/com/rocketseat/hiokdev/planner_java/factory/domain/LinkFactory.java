package br.com.rocketseat.hiokdev.planner_java.factory.domain;

import br.com.rocketseat.hiokdev.planner_java.domain.link.Link;

import java.util.List;
import java.util.UUID;

public class LinkFactory {

    public static Link getLinkWithoutId() {
        return Link.builder()
                .title("Hospedagem booking")
                .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                .build();
    }

    public static Link getLinkWithTripIdAndId(UUID tripId, UUID id) {
        var trip = TripFactory.getTripWithId(tripId);
        return Link.builder()
                .id(id)
                .title("Hospedagem booking")
                .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                .trip(trip)
                .build();
    }

    public static List<Link> getLinkList(UUID tripId) {
        var trip = TripFactory.getTripWithId(tripId);
        return List.of(
                Link.builder()
                        .id(UUID.randomUUID())
                        .title("Hospedagem Booking")
                        .url("https://www.booking.com/city/br/guaruja.pt-br.html")
                        .trip(trip)
                        .build(),
                Link.builder()
                        .id(UUID.randomUUID())
                        .title("Hospedagem Tripadvisor")
                        .url("https://www.tripadvisor.com.br/Hotels-g303610-Guaruja_State_of_Sao_Paulo-Hotels.html")
                        .trip(trip)
                        .build(),
                Link.builder()
                        .id(UUID.randomUUID())
                        .title("Hospedagem Trivago")
                        .url("https://www.trivago.com.br/pt-BR/odr/hot%C3%A9is-guaruj%C3%A1-brasil?search=200-60349")
                        .trip(trip)
                        .build()
        );
    }

}

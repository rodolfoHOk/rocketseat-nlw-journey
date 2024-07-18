package br.com.rocketseat.hiokdev.planner_java.domain.common.dto;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

public record MailData(
        String ownerName,
        String destination,
        String startDate,
        String endDate,
        String confirmationLink,
        String confirmationApp
) {

    @Builder
    public MailData {
    }

    public static MailData create(Trip trip, String confirmationLink) {
        String startDate = trip.getStartsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM"));
        String endDate = trip.getEndsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM' de 'yyyy"));
        return MailData.builder()
                .ownerName(trip.getOwnerName())
                .destination(trip.getDestination())
                .startDate(startDate)
                .endDate(endDate)
                .confirmationLink(confirmationLink)
                .build();
    }

    public static MailData create(Trip trip, String confirmationLink, String confirmationApp) {
        String startDate = trip.getStartsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM"));
        String endDate = trip.getEndsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM' de 'yyyy"));
        return MailData.builder()
                .ownerName(trip.getOwnerName())
                .destination(trip.getDestination())
                .startDate(startDate)
                .endDate(endDate)
                .confirmationLink(confirmationLink)
                .confirmationApp(confirmationApp)
                .build();
    }

}

package br.com.rocketseat.hiokdev.planner_java.infrastructure.mail;

import br.com.rocketseat.hiokdev.planner_java.domain.common.dto.MailData;
import br.com.rocketseat.hiokdev.planner_java.domain.common.gateway.MailGateway;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;

import java.util.Map;

public class MailMessageFactory {

    public static final String BASE_URL = "http://localhost:8080";

    public static MailGateway.MailMessage getConfirmTripMailMessage(Trip trip) {
        String MAIL_SUBJECT = "Planner confirmação de viagem";
        String confirmationLink = BASE_URL + "/trips/" + trip.getId() + "/confirm";
        var mailData = MailData.create(trip, confirmationLink);
        return MailGateway.MailMessage.builder()
                .destination(trip.getOwnerEmail())
                .subject(MAIL_SUBJECT)
                .body("confirm_trip.html")
                .variables(Map.of("mailData", mailData))
                .build();
    }

    public static MailGateway.MailMessage getConfirmPresenceMailMessage(Participant participant) {
        var trip = participant.getTrip();
        String MAIL_SUBJECT = "Planner confirmação presença para a viagem";
        String confirmationLink = BASE_URL + "/trips/" + trip.getId() + "?participant=" + participant.getId();
        String confirmationApp = "planner://trip/" + trip.getId() + "?participant=" + participant.getId();
        var mailData = MailData.create(trip, confirmationLink, confirmationApp);
        return MailGateway.MailMessage.builder()
                .destination(participant.getEmail())
                .subject(MAIL_SUBJECT)
                .body("confirm_presence.html")
                .variables(Map.of("mailData", mailData))
                .build();
    }

    public static MailGateway.MailMessage getMailMessageWithInvalidTemplate(Participant participant) {
        var trip = participant.getTrip();
        String MAIL_SUBJECT = "Planner confirmação presença para a viagem";
        String confirmationLink = BASE_URL + "/trips/" + trip.getId() + "?participant=" + participant.getId();
        String confirmationApp = "planner://trip/" + trip.getId() + "?participant=" + participant.getId();
        var mailData = MailData.create(trip, confirmationLink, confirmationApp);
        return MailGateway.MailMessage.builder()
                .destination(participant.getEmail())
                .subject(MAIL_SUBJECT)
                .body("invalid_template.html")
                .variables(Map.of("mailData", mailData))
                .build();
    }

    public static MailGateway.MailMessage getMailMessageWithInvalidMailData(Participant participant) {
        var trip = participant.getTrip();
        String MAIL_SUBJECT = null;
        String confirmationLink = BASE_URL + "/trips/" + trip.getId() + "?participant=" + participant.getId();
        String confirmationApp = "planner://trip/" + trip.getId() + "?participant=" + participant.getId();
        var mailData = MailData.create(trip, confirmationLink, confirmationApp);
        return MailGateway.MailMessage.builder()
                .destination(participant.getEmail())
                .subject(MAIL_SUBJECT)
                .body("confirm_presence.html")
                .variables(Map.of("mailData", mailData))
                .build();
    }

}

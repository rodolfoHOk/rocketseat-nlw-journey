package br.com.rocketseat.hiokdev.planner_java.factory.domain;

import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;

import java.util.List;
import java.util.UUID;

public class ParticipantFactory {

    public static Participant getOwnerParticipant(Trip trip) {
        return Participant.builder()
                .id(UUID.randomUUID())
                .name(trip.getOwnerName())
                .email(trip.getOwnerEmail())
                .isConfirmed(true)
                .trip(trip)
                .build();
    }

    public static List<Participant> getParticipantsFromInvites(List<String> emailToInvite, Trip trip) {
        return emailToInvite.stream().map(email -> Participant.builder()
                        .id(UUID.randomUUID())
                        .name("")
                        .email(email)
                        .isConfirmed(false)
                        .trip(trip)
                        .build())
                .toList();
    }

    public static Participant getParticipantWithId(UUID id) {
        var trip = TripFactory.getTripWithId(UUID.randomUUID());
        return Participant.builder()
                .id(id)
                .name("")
                .email("mariatest@email.com")
                .isConfirmed(false)
                .trip(trip)
                .build();
    }

    public static Participant getParticipantWithTripIdAndId(UUID tripId, UUID id) {
        var trip = TripFactory.getTripWithId(tripId);
        return Participant.builder()
                .id(id)
                .name("")
                .email("mariatest@email.com")
                .isConfirmed(false)
                .trip(trip)
                .build();
    }

    public static Participant getParticipantToConfirm() {
        return Participant.builder()
                .name("Maria Santos")
                .email("maria@email.com")
                .build();
    }

}

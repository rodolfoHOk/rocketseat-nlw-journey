package br.com.rocketseat.hiokdev.planner_java.participant;

import br.com.rocketseat.hiokdev.planner_java.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream()
                .map(email -> new Participant(email, trip)).toList();
        participants = this.participantRepository.saveAll(participants);
        System.out.println(participants.get(0).getId()); // temp for development only
        Participant ownerParticipant = new Participant(trip);
        ownerParticipant = this.participantRepository.save(ownerParticipant);
        System.out.println(ownerParticipant.getId()); // temp for development only
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {
    }

}

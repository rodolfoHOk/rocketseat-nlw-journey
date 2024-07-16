package br.com.rocketseat.hiokdev.planner_java.domain.participant;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final TripService tripService;

    public void registerParticipantsToTrip(List<String> emailsToInvite, Trip trip) {
        List<Participant> participants = emailsToInvite.stream()
                .map(email -> new Participant(email, trip)).toList();
        this.participantRepository.saveAll(participants);
        Participant ownerParticipant = new Participant(trip);
        this.participantRepository.save(ownerParticipant);
    }

    public List<Participant> getAllParticipantsByTripId(UUID tripId) {
        return this.participantRepository.findByTripId(tripId);
    }

    public Participant registerParticipantToTrip(String email, UUID tripId) {
        var trip = this.tripService.getById(tripId);
        Participant participant = new Participant(email, trip);
        if(trip.getIsConfirmed()) {
            this.triggerConfirmationEmailToParticipant(email);
        }
        return this.participantRepository.save(participant);
    }

    public Participant confirmParticipant(UUID id, Participant participant) {
        var entity = this.participantRepository.findById(id).orElseThrow(() -> new NotFoundException("Participant not found"));
        entity.setIsConfirmed(true);
        return this.participantRepository.save(entity);
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {
    }

    public void triggerConfirmationEmailToParticipant(String email) {
    }

}

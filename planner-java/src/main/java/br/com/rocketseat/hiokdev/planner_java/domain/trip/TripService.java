package br.com.rocketseat.hiokdev.planner_java.domain.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final ParticipantService participantService;

    @Transactional
    public Trip create(Trip trip, List<String> emailsToInvite) {
        var entity = tripRepository.save(trip);
        this.participantService.registerParticipantsToTrip(emailsToInvite, entity);
        return entity;
    }

    public Trip getById(UUID id) {
        return this.tripRepository.findById(id).orElseThrow(() -> new NotFoundException("Trip not found"));
    }

    public Trip update(UUID id, Trip trip) {
        var entity = this.getById(id);
        entity.setDestination(trip.getDestination());
        entity.setStartsAt(trip.getStartsAt());
        entity.setEndsAt(trip.getEndsAt());
        return tripRepository.save(entity);
    }

    public Trip confirm(UUID id) {
        var entity = this.getById(id);
        entity.setIsConfirmed(true);
        entity = tripRepository.save(entity);
        this.participantService.triggerConfirmationEmailToParticipants(entity.getId());
        return entity;
    }

}

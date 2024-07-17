package br.com.rocketseat.hiokdev.planner_java.domain.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripQueryService tripQueryService;
    private final ParticipantService participantService;

    @Transactional
    public Trip create(Trip trip, List<String> emailsToInvite) {
        this.validate(trip);
        var entity = tripRepository.save(trip);
        this.participantService.registerParticipantsToTrip(emailsToInvite, entity);
        return entity;
    }

    public Trip update(UUID id, Trip trip) {
        this.validate(trip);
        var entity = this.tripQueryService.getById(id);
        entity.setDestination(trip.getDestination());
        entity.setStartsAt(trip.getStartsAt());
        entity.setEndsAt(trip.getEndsAt());
        return tripRepository.save(entity);
    }

    public Trip confirm(UUID id) {
        var entity = this.tripQueryService.getById(id);
        entity.setIsConfirmed(true);
        entity = tripRepository.save(entity);
        this.participantService.triggerConfirmationEmailToParticipants(entity.getId());
        return entity;
    }

    private void validate(Trip trip) {
        if (trip.getStartsAt().isBefore(LocalDateTime.now())) {
            throw new ValidationException("starts_at", "data informada já passou");
        }
        if (trip.getEndsAt().isBefore(LocalDateTime.now())) {
            throw new ValidationException("ends_at", "data informada já passou");
        }
        if (trip.getEndsAt().isBefore(trip.getStartsAt())) {
            throw new ValidationException("ends_at", "deve ser depois da data starts_at");
        }
    }

}

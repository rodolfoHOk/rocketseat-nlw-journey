package br.com.rocketseat.hiokdev.planner_java.domain.participant;

import br.com.rocketseat.hiokdev.planner_java.domain.common.dto.MailData;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.gateway.MailGateway;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.Trip;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final String BASE_URL = "http://localhost:5173";
    private final String MAIL_SUBJECT = "Planner confirmação presença para a viagem";

    private final ParticipantRepository participantRepository;
    private final TripQueryService tripQueryService;
    private final MailGateway mailGateway;

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
        var trip = this.tripQueryService.getById(tripId);
        Participant participant = new Participant(email, trip);
        participant = this.participantRepository.save(participant);
        if(trip.getIsConfirmed()) {
            this.triggerConfirmationEmailToParticipant(trip, participant);
        }
        return participant;
    }

    public Participant confirmParticipant(UUID id, Participant participant) {
        var entity = this.participantRepository.findById(id).orElseThrow(() -> new NotFoundException("Participant not found"));
        entity.setName(participant.getName());
        if (participant.getEmail() != null) {
            entity.setEmail(participant.getEmail());
        }
        entity.setIsConfirmed(true);
        return this.participantRepository.save(entity);
    }

    public void triggerConfirmationEmailToParticipants(Trip trip) {
    }

    public void triggerConfirmationEmailToParticipant(Trip trip, Participant participant) {
        String confirmationLink = BASE_URL + "/trips/" + trip.getId() + "?participant=" + participant.getId();
        String confirmationApp = BASE_URL + "planner://trip/" + trip.getId() + "?participant=" + participant.getId();
        var mailData = MailData.create(trip, confirmationLink, confirmationApp);
        var mailMessage = MailGateway.MailMessage.builder()
                .destination(participant.getEmail())
                .subject(MAIL_SUBJECT)
                .body("confirm_presence.html")
                .variables(Map.of("mailData", mailData))
                .build();
        mailGateway.send(mailMessage);
    }

}
